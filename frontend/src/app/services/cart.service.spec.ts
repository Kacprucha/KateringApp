import { TestBed } from '@angular/core/testing';

import { CartService } from './cart.service';
import { KeycloakService } from 'keycloak-angular';
import { MockKeycloakService } from '../../mocks/keycloak/service';

describe('CartService', () => {
  let service: CartService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        {
          provide: KeycloakService,
          useClass: MockKeycloakService,
        },
      ],
    });
    service = TestBed.inject(CartService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
