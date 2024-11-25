import { CanActivateFn } from '@angular/router';
import { isClientEnvironment } from '../shared/utils/environmentGuard';

export const clientEnvironmentGuard: CanActivateFn = (_route, _state) => {
  return isClientEnvironment;
};
