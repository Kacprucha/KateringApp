import { HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthorizationService {
 
  keycloakService = inject(KeycloakService);
  
  redirectToLoginPage(): Promise<void> {
    return this.keycloakService.login();
  }

  get userName(): string {
    return this.keycloakService.getUsername();
  }

  isLoggedIn(): boolean {
    return this.keycloakService.isLoggedIn();
  }

  logout(): void {
    this.keycloakService.logout();
  }

  getToken(): Promise<string> {
    return this.keycloakService.getToken();
  }

  addTokenToHeader(headers?: HttpHeaders): Observable<HttpHeaders>{
    return this.keycloakService.addTokenToHeader(headers);
  }
}
