import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import MealFormComponent from './meal-form.component';
import { RouterModule } from '@angular/router';
import { MealService } from '../../../services/meal/meal.service';

@NgModule({
  declarations: [MealFormComponent],
  imports: [CommonModule, FormsModule, RouterModule],
  providers: [MealService],
  exports: [MealFormComponent],
})
export class MealFormModule {}
