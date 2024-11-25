import { ComponentFixture, TestBed } from '@angular/core/testing';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';
import MealListComponent from './meal-list.component';
import { environment } from '../../../../environments/environment';

const MOCK_MEAL = [
  {
    mealId: 1,
    name: 'Pizza Margherita',
    price: 15.99,
    description: 'A classic pizza with mozzarella, tomatoes, and basil.',
    photo: 'some-url-or-base64-string',
    ingredients: [{ name: 'Cheese', allergens: ['lactose'] }],
  },
];

describe('MealListComponent', () => {
  let component: MealListComponent;
  let fixture: ComponentFixture<MealListComponent>;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MealListComponent],
      providers: [provideHttpClient(), provideHttpClientTesting()],
    }).compileComponents();

    fixture = TestBed.createComponent(MealListComponent);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);

    fixture.detectChanges();

    const req = httpMock.expectOne(`${environment.apiUrl}/api/v1/meal`);
    req.flush(MOCK_MEAL);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should get meal list', () => {
    expect(component.mealList.length).toBe(1);
    expect(component.mealList[0].name).toBe('Pizza Margherita');
    expect(component.mealList[0].photo).toBe(
      'data:image;base64,some-url-or-base64-string',
    );
  });

  it('should delete meal from list', () => {
    component.onDeleteMeal(MOCK_MEAL[0].mealId);

    const requests = httpMock.match(
      `${environment.apiUrl}/api/v1/meal/${MOCK_MEAL[0].mealId}`,
    );
    expect(requests.length).toBe(1);
    const deleteRequest = requests[0];
    expect(deleteRequest.request.method).toBe('DELETE');
    deleteRequest.flush({});

    expect(component.mealList.length).toBe(0);
  });
});
