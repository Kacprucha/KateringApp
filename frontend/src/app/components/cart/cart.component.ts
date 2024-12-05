import { Component, WritableSignal } from '@angular/core';
import { Cart, CartService } from '../../services/cart.service';
import { MealGetDTO } from '../../services/meal/meal.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html'
})
export class CartComponent {
  constructor(private cartService: CartService) {
    this.cartState = this.cartService.cartState;
  }
  cartState: WritableSignal<Cart> | null = null;

  removeFromCart(meal: MealGetDTO) {
    this.cartService.removeFromCart(meal.mealId);
  }

  addToCart(meal: MealGetDTO, quantity: number) {
    this.cartService.addToCart(meal, quantity);
  }
}
