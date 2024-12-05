import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from '../../../environments/environment';

export interface MealCreateDTO {
  name: string;
  price: number; // in cents
  description: string;
  photo: string; // base64
  ingredients: string[];
  cateringFirmId: number;
}

export interface Allergen {
  allergenId: number;
  name: string;
}

export interface Ingredient {
  ingredientId: number;
  name: string;
  allergens: Allergen[];
}

export interface MealGetDTO {
  mealId: number;
  name: string;
  price: number; // in cents
  description: string;
  photo: string; // base64
  ingredients: Ingredient[];
}

@Injectable({
  providedIn: 'root',
})
export class MealService {
  private apiUrl = `${environment.apiUrl}/api/v1/meal`;

  constructor(private http: HttpClient) {}

  createMeal(mealData: MealCreateDTO): Observable<MealGetDTO> {
    return this.http.post<MealGetDTO>(this.apiUrl, mealData);
  }

  getMeals(orderId?: number): Observable<MealGetDTO[]> {
    const params = orderId ? `?orderId=${orderId}` : '';
    return this.http.get<MealGetDTO[]>(`${this.apiUrl}${params}`).pipe(
      map((meals) =>
        meals.map((meal) => {
          return {
            ...meal,
            photo: `data:image;base64,${meal.photo}`,
          };
        }),
      ),
    );
  }

  deleteMeal(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  updateMeal(id: number, mealData: MealCreateDTO): Observable<MealGetDTO> {
    return this.http.put<MealGetDTO>(`${this.apiUrl}/${id}`, mealData);
  }

  getMeal(id: number): Observable<MealGetDTO> {
    return this.http.get<MealGetDTO>(`${this.apiUrl}/${id}`);
  }
}
