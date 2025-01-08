import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { MealStatisticsComponent } from "./meal-statistics.component";
import { CommonModule } from "@angular/common";

@NgModule({
  declarations: [MealStatisticsComponent],
  imports: [FormsModule, CommonModule],
  providers: [],
  exports: [MealStatisticsComponent],
})
export class MealStatisticsModule {}
