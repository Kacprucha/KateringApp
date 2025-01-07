import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  GetOrderDTO,
  OrderService,
} from '../../../services/order/order.service';
import { PaymentService } from '../../../services/payment/payment.service';
import { TransactionFinalizationService } from './payment.service';

@Component({
  selector: 'payment-form',
  templateUrl: './payment-form.component.html',
})
export default class PaypalPaymentPaymentFormComponent implements OnInit {
  paymentService = inject(PaymentService);
  finalizeTransactionService = inject(TransactionFinalizationService);
  orderService = inject(OrderService);
  route = inject(ActivatedRoute);
  router = inject(Router);
  orderId: number | null = null;
  orderDetails: GetOrderDTO | null = null;
  paymentId: string | null = null;

  ngOnInit() {
    this.route.queryParams.subscribe((params) => {
      this.orderId = params['orderId'];
      if (this.orderId) {
        this.orderService.getOrder(this.orderId).subscribe((orderDetails) => {
          this.orderDetails = orderDetails;
          console.log(orderDetails);
          this.paymentService
            .createPayment(orderDetails.orderId)
            .subscribe((payment) => {
              this.paymentId = payment.id;
            });
        });
      }
    });
  }
}
