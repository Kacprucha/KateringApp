import { Component, inject } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PaymentService } from '../../payment.service';

@Component({
  selector: 'paypal-payment-form',
  templateUrl: './paypal.component.html',
})
export default class PaypalPaymentPaymentFormComponent {
  paymentService = inject(PaymentService);
  formBuilder = inject(FormBuilder);
  router = inject(Router);
  paymentForm = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
  });

  onSubmit() {
    if (this.paymentForm.valid) {
      this.paymentService.completeTransaction();
    } else {
      console.log('Form is invalid');
    }
  }
}
