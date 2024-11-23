import { Component, OnInit } from "@angular/core";
import { OrderDTO, OrderService, OrderStatus } from "../../../services/order/order.service";
import { IOrdersWindow } from "../../../services/order/order-list-window.interface";
import { HttpErrorResponse } from "@angular/common/http";

@Component({
    selector: 'order-list',
    templateUrl: './order-list.component.html',
})
export default class OrderListComponent implements OnInit, IOrdersWindow {
    orderList: OrderDTO[] = []
    orderStatusKeys = Object.values(OrderStatus);
    showModal: boolean = false
    selectedOrder!: OrderDTO

    constructor(private orderService: OrderService) {
    }

    ngOnInit(): void {
        this.orderService.getOrders().subscribe({
            next: (orders: OrderDTO[]) => {
              this.showOrders(orders)
            },
            error: (error: HttpErrorResponse) => {
              console.log(
                `I cannot download orders! With status code: ${error.status}, message: ${error.message}`,
              );
            },
        })         
    }

    showOrders(orderList: OrderDTO[]): void {
        // const order: OrderDTO = {
        //     id: 1,
        //     mealIds: [1],
        //     clientId: [1],
        //     opinion: "GOOD",
        //     rate: 100,
        //     orderStatus: OrderStatus.CANCELLED,
        //     startingAddress: "ADDR",
        //     destinationAddress: "DEST"
        // }
        this.orderList = orderList
    }

    showOrder(orderDTO: OrderDTO): void {
        this.orderService.getOrder(orderDTO.id).subscribe({
            next: (order: OrderDTO) => {
              this.selectedOrder = order
            },
            error: (error: HttpErrorResponse) => {
              console.log(
                `I cannot download orders! With status code: ${error.status}, message: ${error.message}`,
              );
            },
        })
        this.selectedOrder = orderDTO
        this.showModal = true 
    }

    closeModal(): void {
        this.showModal = false
    }

    onChangeStatus(order: OrderDTO, event: Event) {
        const newStatus: OrderStatus = (event.target as HTMLSelectElement).value as OrderStatus;
        order.orderStatus = newStatus
        this.orderService.changeOrderStatus(order.id, order).subscribe({
            next: (order: OrderDTO) => {
                this.orderList.forEach(o => {
                    if(o.id === order.id) {
                        o.orderStatus = newStatus
                    }
                })
            },
            error: (error: HttpErrorResponse) => {
                console.log(
                    `I cannot change status of order! With status code: ${error.status}, message: ${error.message}`,
                );
            },
        })
    }
}