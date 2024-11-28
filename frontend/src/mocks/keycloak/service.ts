import { HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { UserRole } from '../../app/types/user-roles';

@Injectable({
  providedIn: 'root',
})
export class MockKeycloakService {
  login(): Promise<void> {
    return Promise.resolve();
  }

  getUsername(): string {
    return 'test-user';
  }

  isLoggedIn(): boolean {
    return true;
  }

  logout(): void {}

  getToken(): Promise<string> {
    return Promise.resolve('mock-token');
  }

  addTokenToHeader(headers?: HttpHeaders): Observable<HttpHeaders> {
    const updatedHeaders = (headers || new HttpHeaders()).set(
      'Authorization',
      'Bearer mock-token',
    );
    return of(updatedHeaders);
  }

  isUserInRole(role: string): boolean {
    return true;
  }
  getUserRoles(): UserRole[] {
    return [UserRole.Client];
  }
}
