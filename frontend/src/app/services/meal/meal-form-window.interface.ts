import { MealDTO } from '../../shared/models/meal-dto';
import { FormError } from '../../types/form-error';

export interface IMealFormWindow {
  removeMeal(id: number): void;
  showErrors(errors: FormError<MealDTO>): void;
  showMealForm(mealDTO: MealDTO): void;
}
