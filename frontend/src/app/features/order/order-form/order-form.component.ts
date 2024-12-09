import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'order-form',
  templateUrl: './order-form.component.html',
})
export default class OrderFormComponent {
  deliveryDetails = {
    name: '',
    surname: '',
    address: '',
    phone: '',
    paymentMethod: '',
  };

  errors = {
    name: '',
    surname: '',
    address: '',
    phone: '',
    paymentMethod: '',
  };

  constructor(private router: Router) {}

  submitOrder(): void {
    this.clearErrors();
    if (this.validateForm()) {
      this.router.navigate(['/summary'], { state: { data: this.deliveryDetails } });
    }
  }

  validateForm(): boolean {
    let isValid = true;

    if (!this.deliveryDetails.name.trim()) {
      this.errors.name = 'Name is required.';
      isValid = false;
    }

    if (!this.deliveryDetails.surname.trim()) {
      this.errors.surname = 'Surname is required.';
      isValid = false;
    }

    if (!this.deliveryDetails.address.trim()) {
      this.errors.address = 'Address is required.';
      isValid = false;
    }

    if (!this.deliveryDetails.phone.trim()) {
      this.errors.phone = 'Phone Number is required.';
      isValid = false;
    }

    if (!this.deliveryDetails.paymentMethod.trim()) {
      this.errors.paymentMethod = 'Please select a payment method.';
      isValid = false;
    }

    return isValid;
  }

  clearErrors(): void {
    this.errors = {
      name: '',
      surname: '',
      address: '',
      phone: '',
      paymentMethod: '',
    };
  }
}
