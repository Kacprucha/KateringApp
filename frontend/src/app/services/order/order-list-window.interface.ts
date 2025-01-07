import { GetOrderDTO } from './order.service';

export interface IOrdersWindow {
  showOrder(mealDTO: GetOrderDTO): void;
  showOrders(orderList: GetOrderDTO[]): void;
}

