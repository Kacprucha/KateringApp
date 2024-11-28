import Keycloak from 'keycloak-js';
import { KeycloakService } from 'keycloak-angular';
import { environment } from './environments/environment';
import { UserRole } from './app/types/user-roles';

export function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak
      .init({
        config: {
          url: 'http://localhost:8081',
          realm: 'kateringapp',
          clientId: 'KateringAppClient',
        },
        initOptions: {
          onLoad: 'login-required',
        },
      })
      .then(() => {
        const keycloakInstance = keycloak.getKeycloakInstance();
        const userRoles = keycloakInstance.realmAccess?.roles;

        const currentUrl = new URL(window.location.href);
        if (userRoles?.includes(UserRole.CateringFirm)) {
          const cateringFirmUiUrl = new URL(environment.cateringFirmUiUrl);
          if (currentUrl.host !== cateringFirmUiUrl.host) {
            window.location.href = cateringFirmUiUrl.href;
          }
        } else if (userRoles?.includes(UserRole.Client)) {
          const clientUiUrl = new URL(environment.clientUiUrl);
          if (currentUrl.host !== clientUiUrl.host) {
            window.location.href = clientUiUrl.href;
          }
        }
      });
}
