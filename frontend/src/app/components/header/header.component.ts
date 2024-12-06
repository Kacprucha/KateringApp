import { Component, inject, OnInit } from '@angular/core';
import { AuthorizationService } from '../../services/authorization.service';
import { isClientEnvironment } from '../../shared/utils/environmentGuard';
import { i18n } from '../../../i18n';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit {
  isClientEnvironment = isClientEnvironment;
  authService = inject(AuthorizationService);
  userName = '';
  userRoles: string[] = [];

  ngOnInit(): void {
    this.loadUserInfo();
  }

  async loadUserInfo() {
    this.userName = await this.authService.getUserName();
    this.userRoles = this.authService
      .getUserRoles()
      .map((role) => i18n.getUserRoleName(role));
  }

  logout(): void {
    this.authService.logout();
  }
}
