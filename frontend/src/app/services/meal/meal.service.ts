import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/env.prod';

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
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });

    return this.http.post<MealGetDTO>(this.apiUrl, mealData, { headers });
  }
}
