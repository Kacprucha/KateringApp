import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import CreditCardPaymentComponent from './forms/credit-card/credit-card.component';
import PaypalPaymentPaymentFormComponent from './forms/paypal/paypal.component';
import PaymentFormComponent from './payment-form.component';

@NgModule({
  declarations: [
    CreditCardPaymentComponent,
    PaypalPaymentPaymentFormComponent,
    PaymentFormComponent,
  ],
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  exports: [PaymentFormComponent],
})
export class PaymentFormModule {}
