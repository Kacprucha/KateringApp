import { Injectable } from '@angular/core';

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
}
