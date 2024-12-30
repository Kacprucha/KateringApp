import { Component, inject } from '@angular/core';

import { CartService } from '../../../services/cart.service';
import { DeliveryDetails } from '../../../types/delivery-details';
import { Router } from '@angular/router';

@Component({
  selector: 'order-summary',
  templateUrl: './order-summary.component.html',
})
export default class OrderSummaryComponent {
  cartService = inject(CartService);
  cart = this.cartService.cartState();
  deliveryDetails: DeliveryDetails = history.state.data;
  router = inject(Router);

  redirectToPaymentForm() {
    const paymentMethod = this.deliveryDetails.paymentMethod;
    this.router.navigateByUrl('/order/checkout', {
      state: { data: { paymentMethod } },
    });
  }
}
