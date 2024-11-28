import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';
import MealUpdateComponent from './meal-update.component';
import { MealService } from '../../../services/meal/meal.service';
import { provideHttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

describe('MealUpdateComponent', () => {
  let component: MealUpdateComponent;
  let fixture: ComponentFixture<MealUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [MealUpdateComponent],
      imports: [FormsModule],
      providers: [
        MealService,
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: {
                get(): string {
                  return '1';
                },
              },
            },
          },
        },],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MealUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should add an ingredient', () => {
    component.newIngredient = 'Tomato';
    component.addIngredient();
    expect(component.meal.ingredients).toContain('Tomato');
  });

  it('should remove an ingredient', () => {
    component.meal.ingredients = ['Tomato', 'Cheese'];
    component.removeIngredient(0);
    expect(component.meal.ingredients).not.toContain('Tomato');
    expect(component.meal.ingredients).toContain('Cheese');
  });

  it('should validate meal form', () => {
    component.meal.name = 'Soup';
    component.meal.description = 'Tasty';
    component.meal.price = -10;
    component.meal.photo = '';
    component.meal.ingredients = [];
    component.validateMeal(component.meal);

    expect(component.errors.name).toBe(
      'Name must be at least 6 characters long.',
    );
    expect(component.errors.description).toBe(
      'Description must be at least 6 characters long.',
    );
    expect(component.errors.price).toBe('Price must be a non-negative number.');
    expect(component.errors.ingredients).toBe(
      'At least one ingredient is required.',
    );
  });

  it('should clear errors on form submit', () => {
    component.meal = {
      id: 1,
      name: 'Valid Meal Name',
      description: 'Valid description',
      price: 10,
      photo: 'photo.png',
      ingredients: ['Ingredient1'],
      cateringFirmId: 1,
    };
    component.errors.name = 'Some error';
    component.onMealFormSubmit();
    expect(component.errors.name).toBeUndefined();
  });

  it('should not submit if there are validation errors', () => {
    spyOn(window, 'alert');
    component.meal.name = 'Soup';
    component.onMealFormSubmit();
    expect(window.alert).not.toHaveBeenCalled();
  });

  it('should change product if there are not validation errors', () => {
    spyOn(component, 'updateMeal').and.callThrough();

    component.meal = {
      id: 1,
      name: 'Valid Meal Name',
      description: 'Delicious meal description',
      price: 10,
      photo: 'photo.png',
      ingredients: ['Ingredient1'],
      cateringFirmId: 1,
    };
    component.clearErrors();
    component.validateMeal(component.meal);
    expect(Object.keys(component.errors).length).toBe(0);

    component.onMealFormSubmit();

    expect(component.updateMeal).toHaveBeenCalledWith(component.meal.id, {
      ...component.meal,
      price: component.meal.price * 100,
    });
  });

  it('should update photo on file change', (done) => {
    const file = new Blob(['photo data'], { type: 'image/png' });
    const fileInput = fixture.debugElement.query(
      By.css('input[type="file"]'),
    ).nativeElement;

    const dataTransfer = new DataTransfer();
    dataTransfer.items.add(
      new File([file], 'photo.png', { type: 'image/png' }),
    );

    fileInput.files = dataTransfer.files;
    fileInput.dispatchEvent(new Event('change'));

    const reader = new FileReader();
    reader.onload = () => {
      const base64Index = (reader.result as string).indexOf(',') + 1;
      const base64Data = (reader.result as string).substring(base64Index);
      expect(component.meal.photo).toBe(base64Data);
      done();
    };
    reader.readAsDataURL(file);
  });
});
