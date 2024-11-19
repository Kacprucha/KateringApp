export interface MealDTO {
  id: number;
  name: string;
  description: string;
  price: number; // in cents
  photo: string; // base64
  ingredients: string[];
  cateringFirmId: number;
}
