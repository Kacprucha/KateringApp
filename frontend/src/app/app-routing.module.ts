import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { cateringFirmEnvironmentGuard } from './guards/catering-firm-environment.guard';
import OrderListComponent from './features/order/order-list/order-list.component';
import MealListComponent from './features/meal/meal-list/meal-list.component';
import MealFormComponent from './features/meal/meal-form/meal-form.component';
import MealUpdateComponent from './features/meal/meal-update-form/meal-update.component';
import OrderFormComponent from './features/order/order-form/order-form.component';
import OrderSummaryComponent from './features/order/order-summary/order-summary.component';
import OrderCheckoutComponent from './features/order/order-checkout/order-checkout.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'meal',
    pathMatch: 'full',
  },
  {
    path: 'orders',
    component: OrderListComponent,
    title: 'KateringApp - Orders',
  },
  {
    path: 'order/form',
    component: OrderFormComponent,
    title: 'KateringApp - Order Form',
  },
  {
    path: 'order/summary',
    component: OrderSummaryComponent,
    title: 'KateringApp - Order Summary',
  },
  {
    path: 'order/checkout',
    component: OrderCheckoutComponent,
    title: 'KateringApp - Order Checkout',
  },
  {
    path: 'meal',
    component: MealListComponent,
    title: 'KateringApp - Meals',
  },
  {
    path: 'meal/create',
    component: MealFormComponent,
    canActivate: [cateringFirmEnvironmentGuard],
    title: 'KateringApp - Create Meal',
  },
  {
    path: 'meal/update/:id',
    component: MealUpdateComponent,
    canActivate: [cateringFirmEnvironmentGuard],
  },
  {
    path: '**',
    component: PageNotFoundComponent,
    title: 'KateringApp - 404',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
