import { Component, OnInit } from "@angular/core";
import { AlertService } from "../../services/alert.service";
import { StatisticsDTO, StatisticsService } from "../../services/statistics/statistics.service";
import Chart from 'chart.js/auto';
import { HttpErrorResponse, HttpResponse } from "@angular/common/http";

@Component({
    selector: 'app-statistics',
    templateUrl: './statistics.component.html',
})
export class StatisticsComponent implements OnInit {
  startDate: string | null = '2015-01-01';
  endDate: string | null = null;
  dataPeriods: string[] = ['WEEK', 'MONTH', 'YEAR'];
  selectedPeriod = 'YEAR';
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

  onPeriodChange(period: string): void {
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
          const dates = res.map(item => this.convertTimestampToDate(item.date));
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
