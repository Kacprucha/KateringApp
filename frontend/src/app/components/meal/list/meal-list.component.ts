import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { environment } from '../../../../environments/env.prod';
import { IMeal } from '../meal';

@Component({
    selector: 'app-meal-list',
    templateUrl: './meal-list.component.html',
  })
export class MealListComponent implements OnInit {
  mealList: IMeal[] = []
  
  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.showMealOfferButtonClicked()
  }

  showMealOfferButtonClicked(): void {
    this.http.get<IMeal[]>(`${environment.apiUrl}/meal`).subscribe({
      next: (response: IMeal[]) => {
        if(response) {
          response.forEach((meal: IMeal) => {
            if(meal) {
              const blob = meal.photo
              meal.photoUrl = URL.createObjectURL(blob)
            }
          })
          this.mealList = response
        }
      },
      error: (error: HttpErrorResponse) => {
        console.log(`I cannot download meals! With status code: ${error.status}, message: ${error.message}`)
        this.mealList = [{
          id: 1,
          name: "Pizza Margherita",
          price: 15.99,
          description: "A classic pizza with mozzarella, tomatoes, and basil.",
          photo: new Blob(),
          ingredients: [{
            name: "Basic ingredients",
            allergens: ["gluten", "lactose"]
          },
          {
            name: "Basic ingredientsv2",
            allergens: ["gluten", "lactosev2"]
          }]
        },
        {
          id: 2,
          name: "Makaron",
          price: 2.22,
          description: "Pesto",
          photo: new Blob(),
          ingredients: [{
            name: "Basic ingredients",
            allergens: ["gluten", "lactose"]
          },
          {
            name: "Basic ingredientsv2",
            allergens: ["gluten", "lactosev2"]
          }]
        }]
      }
    })
  }

  onDeleteMeal(id: number): void {
    const url = `${environment.apiUrl}/meal/${id}`
    this.http.delete<void>(url).subscribe({
      next: () => {
        this.mealList = this.mealList.filter(meal => meal.id !== id)
      },
      error: (error: HttpErrorResponse) => {
        this.mealList = this.mealList.filter(meal => meal.id !== id)
        console.log(`I cannot delete meal! With status code: ${error.status}, message: ${error.message}`)
      }
    })
  }

}
  