import { HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { KeycloakService } from 'keycloak-angular';
import { Observable } from 'rxjs';
import { UserRole } from '../types/user-roles';

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

  isUserInRole(role: string): boolean{
    return this.keycloakService.isUserInRole(role);
  }

  getUserRoles(): UserRole[] {
    const allRoles: string[] = this.keycloakService.getUserRoles(true);
    const validRoles: UserRole[] = allRoles.filter((role): role is UserRole =>
      ['catering-firm	', 'client'].includes(role)
    );
    return validRoles;
  }
}
