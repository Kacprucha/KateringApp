import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MealGetDTO, MealService } from '../../../services/meal/meal.service';
import { MealDTO } from '../../../shared/models/meal-dto';
import { FormError } from '../../../types/form-error';
import { IMealFormWindow } from '../../../services/meal/meal-form-window.interface';
import { IMealFormLogic } from '../../../services/meal/meal-form-logic.interface';

@Component({
  selector: 'app-meal-update',
  templateUrl: './meal-update.component.html',
})
export default class MealUpdateComponent
  implements OnInit, IMealFormWindow, IMealFormLogic {
  meal: MealDTO = {
                  id: 0,
                  name: '',
                  description: '',
                  price: 0,
                  photo: '',
                  ingredients: [],
                  cateringFirmId: 1,
                };

  errors: FormError<MealDTO> = {};
  newIngredient = '';

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private mealService: MealService,
  ) {}

  onRemoveMeal(id: number): void {
    this.removeMeal(id);
  }

  removeMeal(id: number): void {
    console.log(`Removing meal with id: ${id}`);
  }

  ngOnInit(): void {
    let id = Number.parseInt(this.route.snapshot.paramMap.get('id')!);
    
    this.mealService.getMeal(id).subscribe({
      next: (_response: MealGetDTO) => {
        this.meal = {
          id: _response.mealId,
          name: _response.name,
          description: _response.description,
          price: _response.price / 100,
          photo: _response.photo,
          ingredients: _response.ingredients.map(ingredient => ingredient.name),
          cateringFirmId: 1
        }
      },
      error: (error: any) => {
        this.errors.serverErrors = error.message;
      },
    });
  }

  clearErrors() {
    this.errors = {};
  }

  validateMeal(meal: MealDTO) {
    if (meal.name.length < 6) {
      this.errors.name = 'Name must be at least 6 characters long.';
    }
    if (meal.description.length < 6) {
      this.errors.description =
        'Description must be at least 6 characters long.';
    }
    if (meal.price < 0) {
      this.errors.price = 'Price must be a non-negative number.';
    }
    if (meal.ingredients.length === 0) {
      this.errors.ingredients = 'At least one ingredient is required.';
    }
  }

  addIngredient() {
    if (this.newIngredient.trim()) {
      this.meal.ingredients.push(this.newIngredient.trim());
      this.newIngredient = '';
    }
  }

  removeIngredient(index: number) {
    this.meal.ingredients.splice(index, 1);
  }

  showErrors(errors: FormError<MealDTO>) {
    this.errors = errors;
  }

  showMealForm(mealDTO: MealDTO) {
    this.meal = mealDTO;
  }

  updateMeal(id: number, mealDto: MealDTO) {
    this.mealService.updateMeal(this.meal.id, mealDto).subscribe({
      next: (_response: MealGetDTO) => {
        this.router.navigate(['/meal']);
      },
      error: (error: any) => {
        this.errors.serverErrors = error.message;
      },
    });
  }

  onMealFormSubmit() {
    this.clearErrors();
    this.validateMeal(this.meal);
    if (Object.keys(this.errors).length > 0) {
      this.showErrors(this.errors);
      return;
    }

    const payload = {
      ...this.meal,
      price: this.meal.price * 100,
    };

    this.updateMeal(this.meal.id, payload);
  }

  onFileChange(event: Event) {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files[0]) {
      const file = input.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        const result = reader.result as string;
        const base64Index = result.indexOf(',') + 1;
        this.meal.photo = result.substring(base64Index);
      };
      reader.readAsDataURL(file);
    }
  }
}