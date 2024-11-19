import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import MealFormComponent from './meal-form.component';

@NgModule({
  declarations: [MealFormComponent],
  imports: [CommonModule, FormsModule],
  exports: [MealFormComponent],
})
export class MealFormModule {}
