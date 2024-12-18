import { Component, OnInit } from "@angular/core";
import { AlertService } from "../../services/alert.service";
import { StatisticsDTO, StatisticsService } from "../../services/statistics/statistics.service";
import Chart from 'chart.js/auto';
import { HttpErrorResponse, HttpResponse } from "@angular/common/http";

export enum DataPeriod {
  NO_PERIOD = 'NO PERIOD',
  LAST_WEEK = 'LAST WEEK',
  LAST_MONTH = 'LAST MONTH',
  LAST_YEAR = 'LAST YEAR'
}


@Component({
    selector: 'app-statistics',
    templateUrl: './statistics.component.html',
})
export class StatisticsComponent implements OnInit {
  startDate: string | null = '2024-01-01';
  endDate: string | null = null;
  dataPeriods = Object.values(DataPeriod); 
  selectedPeriod: DataPeriod = DataPeriod.LAST_YEAR; 
  maxDate: string = ''
  chart: any = []
  dates: any = []
  sales: any = []

  constructor(private alert: AlertService, private statisticsService: StatisticsService) {
    const today = new Date();
    this.maxDate = today.toISOString().split('T')[0];
    this.endDate = this.maxDate
  }

  ngOnInit(): void {
    this.generateChart()
    this.chart = new Chart('sale-statistic-chart', {
      type: 'line',
      data: {
        labels: this.dates,
        datasets: [
          {
            label: 'Sales statistics',
            data: this.sales,
            borderWidth: 1,
          },
        ],
      },
      options: {
        scales: {
          y: {
            beginAtZero: true,
            title: {
              display: true,
              text: 'Revenue: [PLN]'
            }
          },
          x: {
            title: {
              display: true,
              text: 'Period: ' + this.selectedPeriod
            }
          }
        },
      },
    })
  }

  onPeriodChange(period: DataPeriod): void {
    this.selectedPeriod = period;
    this.chart.options.scales.x.title.text = 'Period: ' + this.selectedPeriod;
    this.generateChart()
  }

  generateChart(): void {
    if(!this.startDate) {
      this.alert.show('Please fulfill start date field!', 'error')
      return
    }
    if(!this.endDate) {
      this.alert.show('Please fulfill end date field!', 'error');
      return
    }
    if(this.startDate > this.endDate) {
      this.alert.show('Start date is greater than end date!', 'error')
    }
    const fromDateTimestamp: string = new Date(this.startDate).toISOString().replace('T', ' ').replace('Z', ''); //otherwise backend throws 400
    const toDateTimestamp: string = new Date(this.endDate).toISOString().replace('T', ' ').replace('Z', ''); //otherwise backend throws 400
    this.statisticsService.getStatistics(fromDateTimestamp, toDateTimestamp, this.selectedPeriod).subscribe({
      next: (res: StatisticsDTO[]) => {
        if(res.length !== 0) {
          console.log("RES: ")
          console.log(res)
          this.fillMissingData(res)
          const dates = res.map(item => item.date);
          const sales = res.map(item => item.sale);
          this.updateChartData(dates, sales)
        } else {
          this.alert.show('No sales data! Please provide another range!', 'error');
        }
      },
      error: (error: HttpErrorResponse) => {
        console.log(
          `I cannot download statistics! With status code: ${error.status}, message: ${error.message}`,
        );
        this.alert.show('Error downloading sale statistics data!', 'error');
      },
    })
  }

  fillMissingData(salesData: StatisticsDTO[]) {
    const todayFormatted = new Date().toISOString().split('T')[0];

    switch(this.selectedPeriod) {
      case DataPeriod.NO_PERIOD:
        if (!this.endDate) {
          this.endDate = todayFormatted          
        }
        if (!this.startDate) {
          this.startDate = '2024-01-01';
        }
        this.fillMissingSalesDataBasedOnPeriod(salesData, this.endDate, this.startDate)
        break;
      case DataPeriod.LAST_WEEK:
        this.fillMissingSalesDataBasedOnPeriod(salesData, todayFormatted, this.generateDateOffset(7, 0, 0))
        break
      case DataPeriod.LAST_MONTH:
        this.fillMissingSalesDataBasedOnPeriod(salesData, todayFormatted, this.generateDateOffset(0, 1, 0))
        break
      case DataPeriod.LAST_YEAR:
        this.fillMissingSalesDataBasedOnPeriod(salesData, todayFormatted, this.generateDateOffset(0, 0, 1))
        break
      default:
        console.log("Invalid period provided!")
    } 
  }

  generateDateOffset(days: number, months: number, years: number) {
    const date = new Date();
    date.setDate(date.getDate() - days);
    date.setMonth(date.getMonth() - months);
    date.setFullYear(date.getFullYear() - years);
    return date.toISOString().split('T')[0]; // Format YYYY-MM-DD
  }

  fillMissingSalesDataBasedOnPeriod(salesData: StatisticsDTO[], endDate: string, startDate: string) {
    const salesMap = new Map();

    salesData.forEach(item => {
      salesMap.set(item.date, item.sale);
    });
  
    const start = new Date(startDate);
    const end = new Date(endDate);
  
    while (start <= end) {
      const formattedDate = start.toISOString().split('T')[0]; // Format YYYY-MM-DD
  
      if (!salesMap.has(formattedDate)) {
        salesData.push({
          date: formattedDate,
          sale: 0
        });
      }

      start.setDate(start.getDate() + 1);
    }

    salesData.sort((a, b) => new Date(a.date).getTime() - new Date(b.date).getTime());

    console.log(salesMap)
    console.log(salesData)
  }

  updateChartData(dates: string[], sales: any): void {
    this.chart.data.labels = dates;
    this.chart.data.datasets[0].data = sales;
    this.chart.update();
    this.sales = sales
    this.dates = dates
  }
  
  convertTimestampToDate(dateString: string) {
    const date = new Date(dateString)
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    
    return `${year}-${month}-${day}`;
  }

}
