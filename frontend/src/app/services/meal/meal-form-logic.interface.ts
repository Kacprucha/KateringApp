import { MealDTO } from '../../shared/models/meal-dto';

export interface IMealFormLogic {
  onMealFormSubmit(mealDTO: MealDTO): void;
  onRemoveMeal(id: number): void;
}
