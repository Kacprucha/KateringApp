import { Component, WritableSignal } from '@angular/core';
import { Cart, CartService } from '../../services/cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html'
})
export class CartComponent {
  constructor(private cartService: CartService) {
    this.cartState = this.cartService.cartState;
  }
  cartState: WritableSignal<Cart> | null = null;
}
