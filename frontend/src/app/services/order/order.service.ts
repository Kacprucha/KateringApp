import { Injectable } from "@angular/core";

import { environment } from '../../../environments/environment';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";


export interface OrderDTO {
    id: number,
    mealIds: number[],
    clientId: number[],
    opinion: string,
    rate: number,
    orderStatus: OrderStatus,
    startingAddress: string,
    destinationAddress: string
}

export enum OrderStatus {
    COMPLETED = 'COMPLETED',
    PENDING = 'PENDING',
    CANCELLED = 'CANCELLED',
}
  

@Injectable({
    providedIn: 'root',
})
export class OrderService {
    private apiUrl = `${environment.apiUrl}/api/v1`;

    constructor(private http: HttpClient) {}

    getOrders(): Observable<OrderDTO[]>  {
        return this.http.get<OrderDTO[]>(this.apiUrl + "/order")
    }

    getOrder(id: number) {
        return this.http.get<OrderDTO>(this.apiUrl + `/order/${id}`)
    }

    changeOrderStatus(id: number, orderDTO: OrderDTO) {
        return this.http.put<OrderDTO>(this.apiUrl + `/order/${id}`, orderDTO)
    }
}