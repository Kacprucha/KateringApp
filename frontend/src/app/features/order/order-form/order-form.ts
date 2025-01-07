import { PaymentMethod } from '../../../services/order/order.service';

export interface OrderForm {
  name: string;
  surname: string;
  address: string;
  phone: string;
  email: string;
  paymentMethod: PaymentMethod;
}
