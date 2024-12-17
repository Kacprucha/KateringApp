import { Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs";

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

    getStatistics(startDate: string, endDate: string, statisticsPeriod: string): Observable<StatisticsDTO[]> {
        const params = new HttpParams()
        .set('startDate', startDate)
        .set('endDate', endDate)
        .set('statisticsPeriod', statisticsPeriod);
        return this.http.get<StatisticsDTO[]>(this.apiUrl + '/stats', { params });
    }

  }