import { Component, inject } from '@angular/core';
import { AuthorizationService } from '../../services/authorization.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
})
export class HeaderComponent {
  authService = inject(AuthorizationService);

  logout(): void {
    this.authService.logout();
  }
}
