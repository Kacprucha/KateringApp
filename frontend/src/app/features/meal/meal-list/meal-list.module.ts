import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import MealListComponent from './meal-list.component';
import { RouterModule } from '@angular/router';
import { MealService } from '../../../services/meal/meal.service';

@NgModule({
  declarations: [MealListComponent],
  imports: [CommonModule, RouterModule],
  providers: [MealService],
  exports: [MealListComponent],
})
export class MealListModule {}
