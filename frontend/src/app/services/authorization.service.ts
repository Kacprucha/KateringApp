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

  async getUserName(): Promise<string> {
    return this.keycloakService
      .loadUserProfile()
      .then((profile) => profile.username!); // username is required in register
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

  addTokenToHeader(headers?: HttpHeaders): Observable<HttpHeaders> {
    return this.keycloakService.addTokenToHeader(headers);
  }

  isUserInRole(role: string): boolean {
    return this.keycloakService.isUserInRole(role);
  }

  getUserRoles(): UserRole[] {
    const allRoles: string[] = this.keycloakService.getUserRoles(true);
    const validRoles: UserRole[] = allRoles.filter((role): role is UserRole =>
      Object.values(UserRole).includes(role as UserRole),
    );
    return validRoles;
  }
}
