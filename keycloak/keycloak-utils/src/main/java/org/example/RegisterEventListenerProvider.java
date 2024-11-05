package org.example;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.*;

public class RegisterEventListenerProvider implements EventListenerProvider {

    private final KeycloakSession session;
    private final RealmProvider model;

    public RegisterEventListenerProvider(KeycloakSession session) {
        this.session = session;
        this.model = session.realms();
    }

    @Override
    public void onEvent(Event event) {

        if (EventType.REGISTER.equals(event.getType())) {

            RealmModel realm = this.model.getRealm(event.getRealmId());
            UserModel user = this.session.users().getUserById(realm, event.getUserId());

            String isCateringFirmString = user.getFirstAttribute("isCateringFirm");
            boolean isCateringFirm = Boolean.parseBoolean(isCateringFirmString);

            if (isCateringFirm) {
                RoleModel companyRole = session.roles().getRealmRole(realm, "catering-firm");
                user.grantRole(companyRole);
            } else {
                RoleModel userRole = session.roles().getRealmRole(realm, "client");
                user.grantRole(userRole);
            }
        }

    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

    }

    @Override
    public void close() {}
}
