import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ClientDTO } from '../shared/models/client-dto';
import { environment } from '../../environments/environment';

export interface ClientUpdateDTO {
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  address: string;
}

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private apiUrl = `${environment.apiUrl}/api/v1/client`;

  constructor(private http: HttpClient) {}
  
  getClient(): Observable<ClientDTO> {
    return this.http.get<ClientDTO>(`${this.apiUrl}`);
  }

  updateClient(client: ClientUpdateDTO): Observable<ClientDTO> {
    return this.http.put<ClientDTO>(`${this.apiUrl}`, client);
  }
}
