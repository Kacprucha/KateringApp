export type DeliveryDetails = {
  name: string;
  surname: string;
  address: string;
  phone: string;
  paymentMethod: 'credit_card' | 'paypal' | 'cash_on_delivery';
};
