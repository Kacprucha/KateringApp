import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {ClientGetDTO, ClientService} from '../../../services/client/client.service';

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
    email: '',
    paymentMethod: '',
  };

  errors = {
    name: '',
    surname: '',
    address: '',
    phone: '',
    email: '',
    paymentMethod: '',
  };

  constructor(private router: Router, private clientService: ClientService) {}

  ngOnInit(): void {
    this.clientService.getClient().subscribe({
      next: (client: ClientGetDTO | null) => {
        if (client) {
          this.deliveryDetails.name = client.firstName;
          this.deliveryDetails.surname = client.lastName;
          this.deliveryDetails.address = client.address;
          this.deliveryDetails.email = client.email;
          this.deliveryDetails.phone = client.phoneNumber;
        }
      },
      error: (error: unknown) => {
        console.error('Failed to fetch client data:', error);
      },
    });
  }

  submitOrder(): void {
    this.clearErrors();
    if (this.validateForm()) {
      this.router.navigate(['/order/summary'], {
        state: { data: this.deliveryDetails },
      });
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

    if (!this.deliveryDetails.email.trim()) {
      this.errors.email = 'Email is required.';
      isValid = false;
    }

    if (!this.deliveryDetails.email.includes('@')) {
      this.errors.email = 'Invalid email address.';
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
      email: '',
      paymentMethod: '',
    };
  }
}
