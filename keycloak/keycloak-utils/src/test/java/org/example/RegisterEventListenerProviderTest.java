package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.keycloak.events.Event;
import org.keycloak.events.EventType;
import org.keycloak.models.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class RegisterEventListenerProviderTest {

    @Mock
    private KeycloakSession session;

    @Mock
    private RealmProvider realmProvider;

    @Mock
    private RoleProvider roleProvider;

    @Mock
    private RealmModel realm;

    @Mock
    private UserProvider userProvider;

    @Mock
    private UserModel user;

    @Mock
    private RoleModel cateringFirmRole;

    @Mock
    private RoleModel clientRole;

    private RegisterEventListenerProvider provider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(session.realms()).thenReturn(realmProvider);
        when(session.users()).thenReturn(userProvider);
        when(session.roles()).thenReturn(roleProvider);
        provider = new RegisterEventListenerProvider(session);
    }

    @Test
    void testOnEventWithCateringFirm() {

        Event event = mock(Event.class);
        when(event.getType()).thenReturn(EventType.REGISTER);
        when(event.getRealmId()).thenReturn("realm-id");
        when(event.getUserId()).thenReturn("user-id");

        when(realmProvider.getRealm("realm-id")).thenReturn(realm);
        when(userProvider.getUserById(realm, "user-id")).thenReturn(user);
        when(roleProvider.getRealmRole(realm, "catering-firm")).thenReturn(cateringFirmRole);
        when(user.getFirstAttribute("isCateringFirm")).thenReturn("true");

        provider.onEvent(event);

        verify(user).grantRole(cateringFirmRole);
        verify(user, never()).grantRole(clientRole);
    }

    @Test
    void testOnEventWithClient() {

        Event event = mock(Event.class);
        when(event.getType()).thenReturn(EventType.REGISTER);
        when(event.getRealmId()).thenReturn("realm-id");
        when(event.getUserId()).thenReturn("user-id");

        when(realmProvider.getRealm("realm-id")).thenReturn(realm);
        when(userProvider.getUserById(realm, "user-id")).thenReturn(user);
        when(roleProvider.getRealmRole(realm, "client")).thenReturn(clientRole);
        when(user.getFirstAttribute("isCateringFirm")).thenReturn("false");

        provider.onEvent(event);

        verify(user).grantRole(clientRole);
        verify(user, never()).grantRole(cateringFirmRole);
    }
}