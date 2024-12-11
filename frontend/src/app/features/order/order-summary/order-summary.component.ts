import { Component, inject } from '@angular/core';

import { CartService } from '../../../services/cart.service';
import { DeliveryDetails } from '../../../types/delivery-details';

@Component({
  selector: 'order-summary',
  templateUrl: './order-summary.component.html',
})
export default class OrderSummaryComponent {
  cartService = inject(CartService);
  cart = this.cartService.cartState();
  deliveryDetails: DeliveryDetails = history.state.data;
}
