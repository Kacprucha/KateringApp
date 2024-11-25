import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { provideHttpClient } from '@angular/common/http';
import { MealFormModule } from './features/meal-form/meal-form.module';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { initializeKeycloak } from '../keycloak-init';
import { MealListModule } from './features/meal-list/meal-list.module';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    PageNotFoundComponent,
    LandingPageComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, MealFormModule, MealListModule, KeycloakAngularModule],
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
