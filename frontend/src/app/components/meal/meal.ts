export interface IMeal {
    id: number //no definition in diagram model 
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