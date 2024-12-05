import { Injectable, signal, WritableSignal } from '@angular/core';
import { MealGetDTO } from './meal/meal.service';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  constructor() { }

  public cartState: WritableSignal<Cart> = signal(defaultCartState);

  public addToCart(meal: MealGetDTO, quantity: number) {
    this.cartState.update(cart => {
      let product = cart.products.find(p => p.meal.mealId === meal.mealId);
      if (product) {
        product.quantity += quantity;
      } else {
        cart.products.push({ meal, quantity });
      }
      cart.productCount += quantity;
      return cart;
    })
  }

  public removeFromCart(mealId: number) {
    this.cartState.update(cart => {
      let product = cart.products.find(p => p.meal.mealId === mealId);
      if (product) {
        cart.productCount -= product.quantity;
        product.quantity -= 1;
        if (product.quantity === 0) {
          cart.products = cart.products.filter(p => p.meal.mealId !== mealId);
        }
      }
      return cart;
    })
  }
}

export interface Cart {
  userId: string;
  productCount: number;
  products: CartItem[];
}

export interface CartItem {
  meal: MealGetDTO;
  quantity: number;
}

const defaultCartState = {
  userId: '',
  productCount: 3,
  products: []
}
