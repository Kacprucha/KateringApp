import { ComponentFixture, TestBed } from '@angular/core/testing';
import { OrderDTO, OrderService, OrderStatus } from '../../../services/order/order.service';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import { environment } from '../../../../environments/env.prod';
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
        expect(component.orderList.length).toBe(2)
        expect(component.orderList[0].opinion).toBe("GOOD")
        expect(component.orderList[0].orderStatus).toBe(OrderStatus.PENDING)
    });

    it('should set selectedOrder and open modal on successful order fetch', () => {
        component.showOrder(component.orderList[0]);
    
        expect(component.selectedOrder).toEqual(component.orderList[0]);
        expect(component.showModal).toBeTrue();
    });
});
