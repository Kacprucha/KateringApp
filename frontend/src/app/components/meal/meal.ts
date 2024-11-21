export interface IMeal {
    mealId: number  
    name: string,
    price: number
    description: string
    photo: Blob
    photoUrl?: string //used only to display
    ingredients: IIngredients[] 
}

export interface IIngredients {
    name: string,
    allergens: string[]
}