import { Component, inject, input } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TransactionFinalizationService } from '../../payment.service';

@Component({
  selector: 'paypal-payment-form',
  templateUrl: './paypal.component.html',
})
export default class PaypalPaymentPaymentFormComponent {
  finalizationService = inject(TransactionFinalizationService);
  formBuilder = inject(FormBuilder);
  router = inject(Router);
  paymentId = input.required<string>();
  paymentForm = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
  });

  onSubmit() {
    if (this.paymentForm.valid) {
      this.finalizationService.completeTransaction(this.paymentId());
    }
  }
}
