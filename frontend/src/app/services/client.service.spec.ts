import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { environment } from '../../environments/environment';
import { ClientService } from './client.service';

describe('ClientService', () => {
  let service: ClientService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        ClientService,
        provideHttpClient(),
        provideHttpClientTesting()
      ],
    });

    service = TestBed.inject(ClientService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should retrieve client data using getClient()', () => {
    const mockClient = {
      clientId: 1,
      firstName: 'John',
      lastName: 'Doe',
      email: 'john.doe@example.com',
      phoneNumber: '+123456789',
      address: '123 Main St',
    };

    service.getClient().subscribe((data) => {
      expect(data).toEqual(mockClient);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/api/v1/client`);
    expect(req.request.method).toBe('GET');
    req.flush(mockClient);
  });

  it('should update client data using updateClient()', () => {
    const clientUpdate = {
      firstName: 'Jane',
      lastName: 'Smith',
      email: 'jane.smith@example.com',
      phoneNumber: '+987654321',
      address: '456 Another St',
    };
    const updatedClient = {
      ...clientUpdate,
      clientId: 1,
    };

    service.updateClient(clientUpdate).subscribe((data) => {
      expect(data).toEqual(updatedClient);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/api/v1/client`);
    expect(req.request.method).toBe('PUT');
    req.flush(updatedClient);
  });
});