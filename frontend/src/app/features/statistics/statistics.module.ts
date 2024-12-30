import { NgModule } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { StatisticsComponent } from "./statistics.component";
import { CommonModule } from "@angular/common";

@NgModule({
    declarations: [StatisticsComponent],
    imports: [FormsModule, CommonModule],
    providers: [],
    exports: [],
  })
export class StatisticsModule {
}