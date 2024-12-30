import { ComponentFixture, TestBed } from '@angular/core/testing';
import { OrderDTO, OrderStatus } from '../../../services/order/order.service';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/environment';
import OrderListComponent from './order-list.component';
import OrderDetails from '../order-details/order-details.component';

const MOCK_ORDERS: OrderDTO[] = [
  {
    id: 1,
    mealIds: [1],
    clientId: [1],
    opinion: 'GOOD',
    rate: 100,
    orderStatus: OrderStatus.PENDING,
    startingAddress: 'ADDR1',
    destinationAddress: 'DEST1',
    contactData: {
      name: 'John',
      surname: 'Doe',
      email: 'john.doe@example.com',
      phoneNumber: '123456789',
      orderDateTime: '2023-12-01T10:00:00',
      dueDateTime: '2023-12-02T10:00:00',
    },
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
    contactData: {
      name: 'Jane',
      surname: 'Smith',
      email: 'jane.smith@example.com',
      phoneNumber: '987654321',
      orderDateTime: '2023-12-03T12:00:00',
      dueDateTime: '2023-12-04T12:00:00',
    },
  },
];

describe('OrderListComponent', () => {
  let component: OrderListComponent;
  let fixture: ComponentFixture<OrderListComponent>;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [OrderListComponent, OrderDetails],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(OrderListComponent);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);

    fixture.detectChanges();

    const req = httpMock.expectOne(`${environment.apiUrl}/api/v1/order`);
    req.flush(MOCK_ORDERS);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load orders on initialization', () => {
    expect(component.orderList.length).toBe(2);
    expect(component.orderList[0].opinion).toBe('GOOD');
    expect(component.orderList[0].orderStatus).toBe(OrderStatus.PENDING);
    expect(component.orderList[0].contactData.name).toBe('John');
  });

  it('should set selectedOrder and open modal on successful order fetch', () => {
    httpMock = TestBed.inject(HttpTestingController);
    component.showOrder(component.orderList[0]);
    const req2 = httpMock.expectOne(
      'http://localhost:8080/api/v1/meal?orderId=1',
    );
    req2.flush([]);
    expect(component.selectedOrder).toEqual(component.orderList[0]);
    expect(component.showModal).toBeTrue();
  });
});
