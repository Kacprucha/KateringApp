import { Component, OnInit } from '@angular/core';
import { ClientService, ClientUpdateDTO } from '../../services/client.service';
import { ClientDTO } from '../../shared/models/client-dto';
import { NgForm } from '@angular/forms';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
})
export class ProfileComponent implements OnInit {
  client: ClientDTO = {
    clientId: -1,
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
    address: "",
  };
  clientUpdate: ClientUpdateDTO = {
    firstName: "",
    lastName: "",
    email: "",
    phoneNumber: "",
    address: "",
  };
  isEditing = false;
  errorMessage: string | null = null;
  formErrors: { [key: string]: string } = {};

  constructor(private clientService: ClientService) {}

  ngOnInit(): void {
    this.loadClient();
  }

  loadClient()
  {
    this.clientService.getClient().subscribe({
      next: (data) => {
        if(data == null)
          return;

        this.client = data;
        this.client.phoneNumber = this.client.phoneNumber.replaceAll(' ', '');
        this.errorMessage = null;
      },
      error: (err) => {
        this.errorMessage = 'Failed to fetch user data. Please try again.';
      },
    });
  }

  toggleEdit() {
    this.clientUpdate = {
      firstName: this.client.firstName,
      lastName: this.client.lastName,
      email: this.client.email,
      phoneNumber: this.client.phoneNumber,
      address: this.client.address
    };
    
    this.isEditing = !this.isEditing;
    this.errorMessage = null;
  }

  saveChanges(form: NgForm): void {
    if (!this.validateForm(form)) {
      return;
    }

    this.clientService.updateClient(this.clientUpdate).subscribe({
      next: () => {
        this.isEditing = false;
        this.errorMessage = null;

        this.loadClient();
      },
      error: (err) => {
        this.errorMessage = 'Failed to update user data. Please try again.';
      },
    });
  }

  validateForm(form: NgForm): boolean {
    this.formErrors = {};

    if (form.controls['firstName']?.invalid) {
      this.formErrors['firstName'] = 'First name is required.';
    }
    if (form.controls['lastName']?.invalid) {
      this.formErrors['lastName'] = 'Last name is required.';
    }
    if (form.controls['email']?.invalid) {
      if (form.controls['email'].errors?.['required']) {
        this.formErrors['email'] = 'Email is required.';
      } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.clientUpdate.email)) { 
        this.formErrors['email'] = 'Provide a valid email address.';
      }
    }
    if (form.controls['phone']?.invalid) {
      if (form.controls['phone'].errors?.['required']) {
        this.formErrors['phone'] = 'Phone number is required.';
      } else if (!/^\+?[0-9]{9,15}$/.test(this.clientUpdate.phoneNumber)) { 
        this.formErrors['phone'] = 'Provide a valid phone number (e.g. +48123456789).';
      }
    }
    if (form.controls['address']?.invalid) {
      this.formErrors['address'] = 'Address is required.';
    }

    return Object.keys(this.formErrors).length === 0;
  }
}