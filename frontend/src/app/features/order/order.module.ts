import { CommonModule } from '@angular/common';
import OrderListComponent from './order-list/order-list.component';
import { RouterModule } from '@angular/router';
import { OrderService } from '../../services/order/order.service';
import { NgModule } from '@angular/core';
import OrderDetails from './order-details/order-details.component';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [OrderListComponent, OrderDetails],
  imports: [CommonModule, RouterModule, FormsModule],
  providers: [OrderService],
  exports: [OrderListComponent],
})
export class OrderModule {}

