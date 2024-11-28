import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthorizationService } from './services/authorization.service';
import { from } from 'rxjs';
import { switchMap } from 'rxjs/operators';

export const httpInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthorizationService);

  return from(authService.getToken()).pipe(
    switchMap((authToken) => {
      const modifiedReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${authToken}`,
        },
      });
      return next(modifiedReq);
    }),
  );
};
