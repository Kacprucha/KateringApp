import { Injectable, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CartService } from '../../../services/cart.service';

@Injectable({
  providedIn: 'root',
})
export class PaymentService {
  router = inject(Router);
  cartService = inject(CartService);

  completeTransaction(): void {
    this.cartService.clearCart();
    this.router.navigate(['/orders']);
  }
}
