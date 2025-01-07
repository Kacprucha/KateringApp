import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CartService } from '../../../services/cart.service';
import { PaymentService } from '../../../services/payment/payment.service';

@Injectable({
  providedIn: 'root',
})
export class TransactionFinalizationService {
  router = inject(Router);
  cartService = inject(CartService);
  paymentService = inject(PaymentService);

  completeTransaction(paymentId: string) {
    this.paymentService.processPayment(paymentId).subscribe(() => {
      this.cartService.clearCart();
      this.router.navigate(['/orders']);
    });
  }
}
