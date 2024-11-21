import { Component } from '@angular/core';
import { AuthorizationService } from '../../services/authorization.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
})
export class HeaderComponent {
  constructor(private readonly authService: AuthorizationService) {}

  logout(): void {
    this.authService.logout();
  }
}
