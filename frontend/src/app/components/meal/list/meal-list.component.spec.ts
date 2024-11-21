import { ComponentFixture, TestBed } from "@angular/core/testing";
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { MealListComponent } from "./meal-list.component"
import { IMeal } from "../meal";
import { environment } from "../../../../environments/env.prod";
import { provideHttpClient } from "@angular/common/http";

describe('MealListComponent', () => {
    let component: MealListComponent;
    let fixture: ComponentFixture<MealListComponent>;
    let httpMock: HttpTestingController;
  
    beforeEach(async () => {
      await TestBed.configureTestingModule({
        declarations: [MealListComponent],
        providers: [provideHttpClient(), provideHttpClientTesting()]
      }).compileComponents();

      fixture = TestBed.createComponent(MealListComponent);
      component = fixture.componentInstance;
      httpMock = TestBed.inject(HttpTestingController);

      fixture.detectChanges();
    });

    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should get meal list', () => {
      const mockMeal: IMeal[] = [
        {
          mealId: 1,
          name: 'Pizza Margherita',
          price: 15.99,
          description: 'A classic pizza with mozzarella, tomatoes, and basil.',
          photo: new Blob(),
          ingredients: [{ name: 'Cheese', allergens: ['lactose'] }]
        }]
        
      const request = httpMock.expectOne(`${environment.apiUrl}/api/v1/meal`)
      expect(request.request.method).toBe("GET")
      request.flush(mockMeal)

      expect(component.mealList.length).toBe(1)
      expect(component.mealList[0].photoUrl).toBeTruthy()
      expect(component.mealList[0].name).toBe('Pizza Margherita')
    })

    
    it('should delete meal from list', () => {
        const id = 1
        const mockMeal: IMeal[] = [
          {
            mealId: 1,
            name: 'Pizza Margherita',
            price: 15.99,
            description: 'A classic pizza with mozzarella, tomatoes, and basil.',
            photo: new Blob(),
            ingredients: [{ name: 'Cheese', allergens: ['lactose'] }]
          }]

        component.mealList = mockMeal
        fixture.detectChanges(); 

        component.onDeleteMeal(id)
  
        const requests = httpMock.match(`${environment.apiUrl}/api/v1/meal/${id}`);
        const deleteRequest = requests[0]
        expect(deleteRequest.request.method).toBe("DELETE")
        deleteRequest.flush({})
  
        expect(component.mealList.length).toBe(0)
      })
  });