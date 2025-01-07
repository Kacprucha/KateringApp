import { Injectable, signal, WritableSignal } from '@angular/core';
import { MealGetDTO } from './meal/meal.service';
import { AuthorizationService } from './authorization.service';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  constructor(private authService: AuthorizationService) {
    this.retrieveCart();
  }

  public cartState: WritableSignal<Cart> = signal(defaultCartState);

  public addToCart(meal: MealGetDTO, quantity: number) {
    this.cartState.update((cart) => {
      let product = cart.products.find((p) => p.meal.mealId === meal.mealId);
      if (product) {
        product.quantity += quantity;
      } else {
        cart.products.push({ meal, quantity });
      }
      cart.productCount += quantity;
      cart.total += meal.price * quantity;
      return cart;
    });

    this.saveCartState();
  }

  public removeFromCart(mealId: number) {
    this.cartState.update((cart) => {
      let product = cart.products.find((p) => p.meal.mealId === mealId);
      if (product) {
        cart.productCount -= 1;
        cart.total -= product.meal.price;
        product.quantity -= 1;
        if (product.quantity === 0) {
          cart.products = cart.products.filter((p) => p.meal.mealId !== mealId);
        }
      }
      return cart;
    });

    this.saveCartState();
  }

  public clearCart() {
    this.cartState.set(defaultCartState);
    this.saveCartState();
  }

  private async retrieveCart() {
    const username = await this.authService.getUserName();
    const cart = localStorage.getItem(`cart-${username}`);
    if (cart) {
      this.cartState.set(JSON.parse(cart));
    } else {
      this.cartState.set({
        username,
        productCount: 0,
        total: 0,
        products: [],
      });
    }
  }

  private async saveCartState() {
    const username = await this.authService.getUserName();
    localStorage.setItem(`cart-${username}`, JSON.stringify(this.cartState()));
  }
}

export interface Cart {
  username: string;
  productCount: number;
  total: number;
  products: CartItem[];
}

export interface CartItem {
  meal: MealGetDTO;
  quantity: number;
}

const defaultCartState: Cart = {
  username: '',
  productCount: 0,
  total: 0,
  products: [],
};
