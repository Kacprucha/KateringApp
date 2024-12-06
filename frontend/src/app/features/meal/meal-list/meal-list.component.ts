import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MealGetDTO, MealService } from '../../../services/meal/meal.service';
import { isCateringFirmEnvironment, isClientEnvironment } from '../../../shared/utils/environmentGuard';
import { Router } from '@angular/router';
import { CartService } from '../../../services/cart.service';


@Component({
  selector: 'app-meal-list',
  templateUrl: './meal-list.component.html',
})
export default class MealListComponent implements OnInit {
  isCateringFirmEnvironment = isCateringFirmEnvironment;
  isClientEnvironment = isClientEnvironment;
  mealList: MealGetDTO[] = [];
  isMealModal: boolean = false
  modalText: string = ""

  constructor(
    private mealService: MealService,
    private router: Router,
    private cartService: CartService,
  ) {}

  ngOnInit(): void {
    this.showMealOfferButtonClicked();
  }

  closeModal(): void {
    this.isMealModal = false
    this.modalText = ""
  }

  onAddToCart(meal: MealGetDTO): void {
    this.cartService.addToCart(meal, 1);
  }

  showMealOfferButtonClicked(): void {
    this.mealService.getMeals().subscribe({
      next: (meals: MealGetDTO[]) => {
        this.mealList = meals;
      },
      error: (error: HttpErrorResponse) => {
        console.log(
          `I cannot download meals! With status code: ${error.status}, message: ${error.message}`,
        );
        this.isMealModal = true
        this.modalText = "I cannot download meals!"
      },
    });
  }

  onDeleteMeal(id: number): void {
    this.mealService.deleteMeal(id).subscribe({
      next: () => {
        this.mealList = this.mealList.filter((meal) => meal.mealId !== id);
      },
      error: (error: HttpErrorResponse) => {
        console.log(
          `I cannot delete meal! With status code: ${error.status}, message: ${error.message}`,
        );
        this.isMealModal = true
        this.modalText = "I cannot delete meal! Meal is already linked to order!"
      },
    });
  }

  onEditMeal(id: number): void {
    this.router.navigate(['/meal/update/' + id]);
  }
}
