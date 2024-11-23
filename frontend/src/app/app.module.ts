import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { provideHttpClient } from '@angular/common/http';
import { MealFormModule } from './features/meal/meal-form/meal-form.module';
import { MealListModule } from './features/meal/meal-list/meal-list.module';
import { OrderModule } from './features/order/order.module';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    PageNotFoundComponent,
    LandingPageComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, MealFormModule, MealListModule, OrderModule],
  providers: [provideHttpClient()],
  bootstrap: [AppComponent],
})
export class AppModule {}
