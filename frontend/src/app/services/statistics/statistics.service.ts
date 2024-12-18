import { Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";
import { DataPeriod } from "../../features/statistics/statistics.component";

export interface StatisticsDTO {
    date: string,
    sale: number
}

@Injectable({
    providedIn: 'root',
  })
  export class StatisticsService {
    private apiUrl = `${environment.apiUrl}/api/v1`;

    constructor(private http: HttpClient) {}

    getStatistics(startDate: string, endDate: string, statisticsPeriod: DataPeriod): Observable<StatisticsDTO[]> {
        const periodParam = this.mapDataPeriodToParam(statisticsPeriod)

        const params = this.buildHttpParams(startDate, endDate, periodParam);

        return this.http.get<StatisticsDTO[]>(this.apiUrl + '/stats', { params });
    }

    mapDataPeriodToParam(period: DataPeriod): string | null {
      switch (period) {
          case DataPeriod.LAST_WEEK:
              return 'WEEK';
          case DataPeriod.LAST_MONTH:
              return 'MONTH';
          case DataPeriod.LAST_YEAR:
              return 'YEAR';
          case DataPeriod.NO_PERIOD:
              return null
          default:
              return null;
        }
    }

    private buildHttpParams(startDate: string, endDate: string, periodParam: string | null): HttpParams {
        if (periodParam) {
          return new HttpParams()
          .set('startDate', startDate)
          .set('endDate', endDate)
          .set('statisticsPeriod', periodParam)
        } else {
          return new HttpParams()
          .set('startDate', startDate)
          .set('endDate', endDate)
        }

    }

  }