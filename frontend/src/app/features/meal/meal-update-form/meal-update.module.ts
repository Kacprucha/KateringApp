import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MealService } from '../../../services/meal/meal.service';
import MealUpdateComponent from './meal-update.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [MealUpdateComponent],
  imports: [CommonModule, RouterModule, FormsModule],
  providers: [MealService],
  exports: [MealUpdateComponent],
})
export class MealUpdateModule {}
