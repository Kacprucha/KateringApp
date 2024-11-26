import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { provideHttpClient } from '@angular/common/http';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { initializeKeycloak } from '../keycloak-init';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { MealFormModule } from './features/meal/meal-form/meal-form.module';
import { MealListModule } from './features/meal/meal-list/meal-list.module';
import { MealUpdateModule } from './features/meal/meal-update-form/meal-update.module';
import { OrderModule } from './features/order/order.module';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    PageNotFoundComponent,
    LandingPageComponent
  ],
  imports: [BrowserModule, AppRoutingModule, MealFormModule, MealUpdateModule, MealListModule, KeycloakAngularModule, OrderModule],
  providers: [
    provideHttpClient(),
    {
    provide: APP_INITIALIZER,
    useFactory: initializeKeycloak,
    multi: true,
    deps: [KeycloakService]
  }],
  bootstrap: [AppComponent],
})
export class AppModule {}
