import { Component, inject } from '@angular/core';

import { CartService } from '../../../services/cart.service';

@Component({
  selector: 'order-checkout',
  templateUrl: './order-checkout.component.html',
})
export default class OrderCheckoutComponent {
  cartService = inject(CartService);
  cart = this.cartService.cartState();
}
