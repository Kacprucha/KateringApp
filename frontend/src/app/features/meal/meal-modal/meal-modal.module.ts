import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MealService } from '../../../services/meal/meal.service';
import MealModal from './meal-modal.component';

@NgModule({
  declarations: [MealModal],
  imports: [CommonModule, RouterModule],
  providers: [MealService],
  exports: [MealModal],
})
export class MealModalModule {}
