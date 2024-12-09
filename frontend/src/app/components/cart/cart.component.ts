import { Component, WritableSignal } from '@angular/core';
import { Cart, CartService } from '../../services/cart.service';
import { MealGetDTO } from '../../services/meal/meal.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html'
})
export class CartComponent {
  cartState: WritableSignal<Cart> | null = null;

  constructor(
    private cartService: CartService,
    private router: Router,
  ) {
    this.cartState = this.cartService.cartState;
  }

  hideDropdown() {
    const dropdown = document.activeElement as HTMLElement;
    dropdown.blur();
  }

  removeFromCart(meal: MealGetDTO) {
    this.cartService.removeFromCart(meal.mealId);
  }

  addToCart(meal: MealGetDTO, quantity: number) {
    this.cartService.addToCart(meal, quantity);
  }

  goToOrderForm() {
    this.hideDropdown();
    this.router.navigate(['/order/form']);
  }
}
