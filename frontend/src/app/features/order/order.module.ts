import { CommonModule } from '@angular/common';
import OrderListComponent from './order-list/order-list.component';
import { RouterModule } from '@angular/router';
import { OrderService } from '../../services/order/order.service';
import { NgModule } from '@angular/core';
import OrderDetails from './order-details/order-details.component';
import { FormsModule } from '@angular/forms';
import OrderFormComponent from './order-form/order-form.component';
import OrderSummaryComponent from './order-summary/order-summary.component';
import { PaymentFormModule } from './payment-form/payment-form.module';

@NgModule({
  declarations: [
    OrderListComponent,
    OrderDetails,
    OrderFormComponent,
    OrderSummaryComponent,
  ],
  imports: [CommonModule, RouterModule, FormsModule, PaymentFormModule],
  providers: [OrderService],
  exports: [OrderListComponent],
})
export class OrderModule {}
