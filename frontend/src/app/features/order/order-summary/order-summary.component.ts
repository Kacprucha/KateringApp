import { Component, inject } from '@angular/core';

import { CartService } from '../../../services/cart.service';
import { Router } from '@angular/router';
import { OrderService } from '../../../services/order/order.service';
import { OrderForm } from '../order-form/order-form';

@Component({
  selector: 'order-summary',
  templateUrl: './order-summary.component.html',
})
export default class OrderSummaryComponent {
  cartService = inject(CartService);
  cart = this.cartService.cartState();
  deliveryDetails: OrderForm = history.state.data;
  router = inject(Router);
  orderService = inject(OrderService);

  redirectToPaymentForm() {
    this.orderService
      .createOrder({
        mealIds: this.cart.products.map((product) => product.meal.mealId),
        paymentMethod: this.deliveryDetails.paymentMethod,
        contactData: {
          name: this.deliveryDetails.name,
          surname: this.deliveryDetails.surname,
          email: this.deliveryDetails.email,
          phoneNumber: this.deliveryDetails.phone,
          destinationAddress: this.deliveryDetails.address,
        },
      })
      .subscribe((res) => {
        this.router.navigate(['/order/checkout'], {
          queryParams: {
            orderId: res.orderId,
          },
        });
      });
  }
}
