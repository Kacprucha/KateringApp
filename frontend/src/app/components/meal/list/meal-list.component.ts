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
    this.http.get<IMeal[]>(`${environment.apiUrl}/api/v1/meal`).subscribe({
      next: (response: IMeal[]) => {
        if(response) {
          response.forEach((meal: IMeal) => {
            if(meal && meal.photo) {
              const blob = meal.photo
              meal.photoUrl = URL.createObjectURL(blob)
            }
          })
          this.mealList = response
        }
      },
      error: (error: HttpErrorResponse) => {
        console.log(`I cannot download meals! With status code: ${error.status}, message: ${error.message}`)
      }
    })
  }

  onDeleteMeal(id: number): void {
    const url = `${environment.apiUrl}/api/v1/meal/${id}`
    this.http.delete<void>(url).subscribe({
      next: () => {
        this.mealList = this.mealList.filter(meal => meal.mealId !== id)
      },
      error: (error: HttpErrorResponse) => {
        console.log(`I cannot delete meal! With status code: ${error.status}, message: ${error.message}`)
      }
    })
  }

}
  