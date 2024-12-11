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
    this.clientService.getClient().subscribe({
      next: (data) => {
        this.client = data;
        this.errorMessage = null;
      },
      error: (err) => {
        console.error('Błąd podczas pobierania danych użytkownika:', err);
        this.errorMessage = 'Nie udało się pobrać danych użytkownika. Spróbuj ponownie.';
      },
    });
  }

  toggleEdit() {
    this.isEditing = !this.isEditing;
    this.errorMessage = null;
  }

  saveChanges(form: NgForm): void {
    if (!this.validateForm(form)) {
      return;
    }

    this.clientService.updateClient(this.client).subscribe({
      next: () => {
        this.isEditing = false;
        this.errorMessage = null;
      },
      error: (err) => {
        console.error('Błąd podczas zapisu:', err);
        this.errorMessage = 'Nie udało się pobrać danych użytkownika. Spróbuj ponownie.';
      },
    });
  }

  validateForm(form: NgForm): boolean {
    this.formErrors = {};

    if (form.controls['firstName']?.invalid) {
      this.formErrors['firstName'] = 'Imię jest wymagane.';
    }
    if (form.controls['lastName']?.invalid) {
      this.formErrors['lastName'] = 'Nazwisko jest wymagane.';
    }
    if (form.controls['email']?.invalid) {
      if (form.controls['email'].errors?.['required']) {
        this.formErrors['email'] = 'Email jest wymagany.';
      } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(this.clientUpdate.email)) { 
        this.formErrors['email'] = 'Podaj poprawny adres email.';
      }
    }
    if (form.controls['phone']?.invalid) {
      if (form.controls['phone'].errors?.['required']) {
        this.formErrors['phone'] = 'Numer telefonu jest wymagany.';
      } else if (!/^\+?[0-9]{9,15}$/.test(this.clientUpdate.phoneNumber)) { 
        this.formErrors['phone'] = 'Podaj poprawny numer telefonu (np. +48123456789).';
      }
    }
    if (form.controls['address']?.invalid) {
      this.formErrors['address'] = 'Adres jest wymagany.';
    }

    return Object.keys(this.formErrors).length === 0;
  }
}