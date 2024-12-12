import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';
import { ClientService } from '../../services/client.service';
import { ProfileComponent } from './profile.component';
import { ClientDTO } from '../../shared/models/client-dto';

describe('ProfileComponent', () => {
    let component: ProfileComponent;
    let fixture: ComponentFixture<ProfileComponent>;
    let clientService: jasmine.SpyObj<ClientService>;

    beforeEach(async () => {
        const clientServiceMock = jasmine.createSpyObj('ClientService', ['getClient', 'updateClient']);

        clientServiceMock.getClient.and.returnValue(of({
            clientId: 1,
            firstName: 'John',
            lastName: 'Doe',
            email: 'john.doe@example.com',
            phoneNumber: '+48123456789',
            address: '123 Main St',
        }));

        await TestBed.configureTestingModule({
            declarations: [ProfileComponent],
            imports: [FormsModule],
            providers: [
                { provide: ClientService, useValue: clientServiceMock }
            ],
        }).compileComponents();

        clientService = TestBed.inject(ClientService) as jasmine.SpyObj<ClientService>;
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(ProfileComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create the component', () => {
        expect(component).toBeTruthy();
    });

    it('should load client data on initialization', () => {
        const mockClient: ClientDTO = {
            clientId: 1,
            firstName: 'John',
            lastName: 'Doe',
            email: 'john.doe@example.com',
            phoneNumber: '+48123456789',
            address: '123 Main St',
        };
        clientService.getClient.and.returnValue(of(mockClient));

        component.ngOnInit();

        expect(clientService.getClient).toHaveBeenCalled();
        expect(component.client).toEqual(mockClient);
    });

    it('should handle error when loading client data', () => {
        clientService.getClient.and.returnValue(throwError(() => new Error('Error')));

        component.ngOnInit();

        expect(clientService.getClient).toHaveBeenCalled();
        expect(component.errorMessage).toBe('Failed to fetch user data. Please try again.');
    });

    it('should toggle editing mode', () => {
        component.isEditing = false;
        component.client = {
            clientId: 1,
            firstName: 'John',
            lastName: 'Doe',
            email: 'john.doe@example.com',
            phoneNumber: '+48123456789',
            address: '123 Main St',
        };

        component.toggleEdit();

        expect(component.isEditing).toBeTrue();
        expect(component.clientUpdate).toEqual({
            firstName: 'John',
            lastName: 'Doe',
            email: 'john.doe@example.com',
            phoneNumber: '+48123456789',
            address: '123 Main St',
        });
    });

    it('should handle error when saving changes', () => {
        component.clientUpdate = {
            firstName: 'Jane',
            lastName: 'Smith',
            email: 'jane.smith@example.com',
            phoneNumber: '+48123456789',
            address: '456 Another St',
        };
        component.isEditing = true;

        clientService.updateClient.and.returnValue(throwError(() => new Error('Error')));

        component.saveChanges({ valid: true, controls: {} } as any);

        expect(clientService.updateClient).toHaveBeenCalled();
        expect(component.errorMessage).toBe('Failed to update user data. Please try again.');
    });

    it('should validate form correctly', () => {
        const form = {
            controls: {
                firstName: { invalid: false },
                lastName: { invalid: false },
                email: { invalid: true, errors: { required: true } },
                phone: { invalid: false },
                address: { invalid: false },
            },
        } as any;

        const isValid = component.validateForm(form);

        expect(isValid).toBeFalse();
        expect(component.formErrors['email']).toBe('Email is required.');
    });
});
