import { OrderDTO } from "./order.service";

export interface IOrdersWindow {
    showOrder(mealDTO: OrderDTO): void;
    showOrders(orderList: OrderDTO[]): void;
}