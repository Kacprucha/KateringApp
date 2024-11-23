import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { OrderService, OrderDTO, OrderStatus } from './order.service';
import { environment } from '../../../environments/env.prod';

describe('OrderService', () => {
  let service: OrderService;
  let httpMock: HttpTestingController;

  const mockOrders: OrderDTO[] = [
    {
      id: 1,
      mealIds: [1],
      clientId: [1],
      opinion: 'GOOD',
      rate: 100,
      orderStatus: OrderStatus.PENDING,
      startingAddress: 'ADDR1',
      destinationAddress: 'DEST1',
    },
    {
      id: 2,
      mealIds: [2],
      clientId: [2],
      opinion: 'AVERAGE',
      rate: 50,
      orderStatus: OrderStatus.COMPLETED,
      startingAddress: 'ADDR2',
      destinationAddress: 'DEST2',
    },
  ];

  const mockOrder: OrderDTO = {
    id: 1,
    mealIds: [1],
    clientId: [1],
    opinion: 'GOOD',
    rate: 100,
    orderStatus: OrderStatus.PENDING,
    startingAddress: 'ADDR1',
    destinationAddress: 'DEST1',
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [OrderService],
    });

    service = TestBed.inject(OrderService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getOrders', () => {
    it('should fetch all orders', () => {
      service.getOrders().subscribe((orders) => {
        expect(orders).toEqual(mockOrders);
      });

      const req = httpMock.expectOne(`${environment.apiUrl}/api/v1/order`);
      expect(req.request.method).toBe('GET');
      req.flush(mockOrders);
    });

    it('should handle error on getOrders', () => {
      service.getOrders().subscribe({
        next: () => fail('Expected an error, but got a response'),
        error: (error) => {
          expect(error.status).toBe(500);
        },
      });

      const req = httpMock.expectOne(`${environment.apiUrl}/api/v1/order`);
      req.flush('Error fetching orders', { status: 500, statusText: 'Internal Server Error' });
    });
  });

  describe('getOrder', () => {
    it('should fetch a single order by ID', () => {
      service.getOrder(mockOrder.id).subscribe((order) => {
        expect(order).toEqual(mockOrder);
      });

      const req = httpMock.expectOne(`${environment.apiUrl}/api/v1/order/${mockOrder.id}`);
      expect(req.request.method).toBe('GET');
      req.flush(mockOrder);
    });

    it('should handle error on getOrder', () => {
      service.getOrder(mockOrder.id).subscribe({
        next: () => fail('Expected an error, but got a response'),
        error: (error) => {
          expect(error.status).toBe(404);
        },
      });

      const req = httpMock.expectOne(`${environment.apiUrl}/api/v1/order/${mockOrder.id}`);
      req.flush('Order not found', { status: 404, statusText: 'Not Found' });
    });
  });

  describe('changeOrderStatus', () => {
    it('should update the order status', () => {
      const updatedOrder = { ...mockOrder, orderStatus: OrderStatus.COMPLETED };

      service.changeOrderStatus(mockOrder.id, updatedOrder).subscribe((order) => {
        expect(order).toEqual(updatedOrder);
      });

      const req = httpMock.expectOne(`${environment.apiUrl}/api/v1/order/${mockOrder.id}`);
      expect(req.request.method).toBe('PUT');
      expect(req.request.body).toEqual(updatedOrder);
      req.flush(updatedOrder);
    });

    it('should handle error on changeOrderStatus', () => {
      const updatedOrder = { ...mockOrder, orderStatus: OrderStatus.COMPLETED };

      service.changeOrderStatus(mockOrder.id, updatedOrder).subscribe({
        next: () => fail('Expected an error, but got a response'),
        error: (error) => {
          expect(error.status).toBe(400);
        },
      });

      const req = httpMock.expectOne(`${environment.apiUrl}/api/v1/order/${mockOrder.id}`);
      req.flush('Invalid order data', { status: 400, statusText: 'Bad Request' });
    });
  });
});
