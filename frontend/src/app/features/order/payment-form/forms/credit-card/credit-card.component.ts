import { Component, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PaymentService } from '../../payment.service';

@Component({
  selector: 'credit-card-payment-form',
  templateUrl: './credit-card.component.html',
})
export default class CreditCardPaymentComponent {
  paymentService = inject(PaymentService);
  formBuilder = inject(FormBuilder);
  router = inject(Router);
  paymentForm = this.formBuilder.group({
    nameOnCard: ['', [Validators.required, Validators.minLength(3)]],
    cardNumber: ['', [Validators.required, Validators.pattern('^[0-9]{16}$')]],
    expirationDate: [
      '',
      [Validators.required, Validators.pattern('^(0[1-9]|1[0-2])/[0-9]{2}$')],
    ],
    cvv: ['', [Validators.required, Validators.pattern('^[0-9]{3}$')]],
  });

  onSubmit() {
    if (this.paymentForm.valid) {
      this.paymentService.completeTransaction();
    } else {
      console.log('Form is invalid');
    }
  }
}
