import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MealGetDTO, MealService } from '../../services/meal/meal.service';

@Component({
  selector: 'app-meal-list',
  templateUrl: './meal-list.component.html',
})
export default class MealListComponent implements OnInit {
  mealList: MealGetDTO[] = [];

  constructor(private mealService: MealService) {}

  ngOnInit(): void {
    this.showMealOfferButtonClicked();
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
      },
    });
  }
}
