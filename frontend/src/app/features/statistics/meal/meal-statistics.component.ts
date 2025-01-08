import { Component, OnInit } from "@angular/core";
import { AlertService } from "../../../services/alert.service";
import { StatisticsDTO, StatisticsService } from "../../../services/statistics/statistics.service";
import { HttpErrorResponse } from "@angular/common/http";
import { DataPeriod } from "../statistics.component";
import { MealStatisticsDTO } from "../../../services/statistics/statistics.service";

@Component({
  selector: 'app-meal-statistics',
  templateUrl: './meal-statistics.component.html',
})
export class MealStatisticsComponent implements OnInit {
  startDate: string | null = '2024-01-01';
  endDate: string | null = null;
  dataPeriods = Object.values(DataPeriod); 
  selectedPeriod: DataPeriod = DataPeriod.LAST_YEAR; 
  maxDate: string = '';
  mealStatistics: MealStatisticsDTO[] = [];

  constructor(private alert: AlertService, private statisticsService: StatisticsService) {
    const today = new Date();
    this.maxDate = today.toISOString().split('T')[0];
    this.endDate = this.maxDate;
  }

  ngOnInit(): void {
    this.generateList();
    console.log(this.mealStatistics)
  }

  onPeriodChange(period: DataPeriod): void {
    this.selectedPeriod = period;
    this.generateList();
  }

  generateList(): void {
    if (!this.startDate || !this.endDate) {
      this.alert.show('Please provide start and end dates!', 'error');
      return;
    }
    if (this.startDate > this.endDate) {
      this.alert.show('Start date cannot be after end date!', 'error');
      return;
    }

    const fromDate = new Date(this.startDate).toISOString().replace('T', ' ').replace('Z', ''); //otherwise backend throws 400
    const toDate = new Date(this.endDate).toISOString().replace('T', ' ').replace('Z', ''); //otherwise backend throws 400

    this.statisticsService.getMealStatistics(fromDate, toDate, this.selectedPeriod).subscribe({
      next: (res: MealStatisticsDTO[]) => {
        if (res.length !== 0) {
          this.updateListData(res);
        } else {
          this.alert.show('No meal statistics data! Please provide another range!', 'error');
        }
      },
      error: (error: HttpErrorResponse) => {
        console.error(`Error fetching meal statistics: ${error.status}, ${error.message}`);
        this.alert.show('Error fetching meal statistics data!', 'error');
      }
    });
  }

  updateListData(mealStats: MealStatisticsDTO[]): void {
    this.mealStatistics = mealStats.map(meal => {
      return {
        mealId: meal.mealId,
        name: meal.name,
        price: meal.price,
        description: meal.description,
        photo: meal.photo,
        quantitySold: meal.quantitySold,
        totalSalesValue: meal.totalSalesValue,
      };
    });
  }

  sortByquantitySold(): void {
    this.mealStatistics.sort((a, b) => b.quantitySold - a.quantitySold);
  }

  sortByRevenue(): void {
    this.mealStatistics.sort((a, b) => b.totalSalesValue - a.totalSalesValue);
  }
}
