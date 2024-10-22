import { Component, signal, WritableSignal } from '@angular/core';
import { Alert, AlertService } from './services/alert.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
})
export class AppComponent {
  title = 'katering app';

  alert: WritableSignal<Alert>;

  constructor(private alertService: AlertService) {
    // this.alertService.show('Welcome to Katering App', 'info');
    this.alert = this.alertService.alert;
  }

  hideAlert() {
    this.alertService.hide();
  }
}
