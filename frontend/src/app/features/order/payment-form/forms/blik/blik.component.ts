import { Component, inject, input } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TransactionFinalizationService } from '../../payment.service';

@Component({
  selector: 'blik-payment-form',
  templateUrl: './blik.component.html',
})
export default class BlikPaymentComponent {
  finalizationService = inject(TransactionFinalizationService);
  formBuilder = inject(FormBuilder);
  router = inject(Router);
  paymentId = input.required<string>();
  paymentForm = this.formBuilder.group({
    code: ['', [Validators.required, Validators.pattern('^[0-9]{6}$')]],
  });

  onSubmit() {
    if (this.paymentForm.valid) {
      this.finalizationService.completeTransaction(this.paymentId());
    }
  }
}
