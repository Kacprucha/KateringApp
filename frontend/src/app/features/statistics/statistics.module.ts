import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { StatisticsComponent } from "./statistics.component";
import { CommonModule } from "@angular/common";
import { MealStatisticsModule } from "./meal/meal-statistics.module";

@NgModule({
    declarations: [StatisticsComponent],
    imports: [FormsModule, CommonModule, MealStatisticsModule ],
    providers: [],
    exports: [],
  })
export class StatisticsModule {
}