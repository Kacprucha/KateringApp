import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface ClientGetDTO {
  clientId: string;
  firstName: string;
  lastName: string;
  phoneNumber: string;
  address: string;
  email: string;
}

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  private apiUrl = `${environment.apiUrl}/api/v1/client`;

  constructor(private http: HttpClient) {}

  getClient(): Observable<ClientGetDTO | null> {
    return this.http.get<ClientGetDTO | null>(this.apiUrl);
  }
}
