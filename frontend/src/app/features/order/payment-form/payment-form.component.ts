import { Component, inject, OnInit, signal } from '@angular/core';
import { DeliveryDetails } from '../../../types/delivery-details';
import { Router } from '@angular/router';
import { PaymentService } from './payment.service';

@Component({
  selector: 'payment-form',
  templateUrl: './payment-form.component.html',
})
export default class PaypalPaymentPaymentFormComponent implements OnInit {
  paymentService = inject(PaymentService);
  router = inject(Router);
  paymentMethod = signal<DeliveryDetails['paymentMethod'] | undefined>(
    undefined,
  );

  ngOnInit() {
    this.paymentMethod.set(history.state.data.paymentMethod);
    if (this.paymentMethod() === 'cash_on_delivery') {
      this.paymentService.completeTransaction();
    }
  }
}
