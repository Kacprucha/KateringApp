import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { IMealFormLogic } from '../../services/meal/meal-form-logic.interface';
import { MealDTO } from '../../shared/models/meal-dto';
import { FormError } from '../../types/form-error';
import { MealGetDTO, MealService } from '../../services/meal/meal.service';
import { IMealFormWindow } from '../../services/meal/meal-form-window.interface';

@Component({
  selector: 'app-meal-form',
  templateUrl: './meal-form.component.html',
})
export default class MealFormComponent
  implements IMealFormWindow, IMealFormLogic
{
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
    private mealService: MealService,
  ) {}

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
    if (!meal.photo) {
      this.errors.photo = 'Photo is required.';
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

  removeMeal(id: number) {
    console.log(`Removing meal with id: ${id}`);
  }

  showErrors(errors: FormError<MealDTO>) {
    this.errors = errors;
  }

  showMealForm(mealDTO: MealDTO) {
    this.meal = mealDTO;
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

    this.mealService.createMeal(payload).subscribe({
      next: (response: MealGetDTO) => {
        this.router.navigate(['/meal', response.mealId]);
      },
      error: (error: any) => {
        this.errors.serverErrors = error.message;
      },
    });
  }

  onRemoveMeal(id: number) {
    this.removeMeal(id);
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
