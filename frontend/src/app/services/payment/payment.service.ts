import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

export interface PaymentDTO {
  id: string;
  status: string;
  amount: number;
  currency: string;
  description: string;
}

@Injectable({
  providedIn: 'root',
})
export class PaymentService {
  private apiUrl = `${environment.apiUrl}/api/v1/payments`;

  constructor(private http: HttpClient) {}

  getPaymentDetails(id: number) {
    return this.http.get<PaymentDTO>(`${this.apiUrl}/${id}`);
  }

  createPayment(id: number) {
    console.log('createPayment');
    return this.http.post<PaymentDTO>(`${this.apiUrl}/create`, {
      orderId: id,
      currency: 'PLN',
      description: `Order ${id} payment`,
    });
  }

  processPayment(id: string) {
    return this.http.post<PaymentDTO>(`${this.apiUrl}/${id}/process`, null);
  }
}
