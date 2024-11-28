import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MealService } from '../../../services/meal/meal.service';
import MealListComponent from './meal-list.component';
import { MealModalModule } from '../meal-modal/meal-modal.module';

@NgModule({
  declarations: [MealListComponent],
  imports: [CommonModule, RouterModule, MealModalModule],
  providers: [MealService],
  exports: [MealListComponent],
})
export class MealListModule {}
