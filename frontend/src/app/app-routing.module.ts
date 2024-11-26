import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { cateringFirmEnvironmentGuard } from './guards/catering-firm-environment.guard';
import OrderListComponent from './features/order/order-list/order-list.component';
import MealListComponent from './features/meal/meal-list/meal-list.component';
import MealFormComponent from './features/meal/meal-form/meal-form.component';
import MealUpdateComponent from './features/meal/meal-update-form/meal-update.component';

const routes: Routes = [
  {
    path: '',
    component: LandingPageComponent,
  },
  {
    path: 'orders',
    component: OrderListComponent
  },
  {
    path: 'meal',
    component: MealListComponent,
  },
  {
    path: 'meal/create',
    component: MealFormComponent,
    canActivate: [cateringFirmEnvironmentGuard],
  },
  {
    path: 'meal/update/:id',
    component: MealUpdateComponent,
    canActivate: [cateringFirmEnvironmentGuard],
  },
  {
    path: '**',
    component: PageNotFoundComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
