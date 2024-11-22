import { KeycloakService } from 'keycloak-angular';

export function initializeKeycloak(keycloak: KeycloakService) {
  return () =>
    keycloak.init({
      config: {
        url: 'http://localhost:8081',
        realm: 'kateringapp',
        clientId: 'KateringAppClient',
      },
      initOptions: {
        onLoad: 'login-required',
      },
    });
}
