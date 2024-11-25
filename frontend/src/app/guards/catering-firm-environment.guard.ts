import { CanActivateFn } from '@angular/router';
import { isCateringFirmEnvironment } from '../shared/utils/environmentGuard';

export const cateringFirmEnvironmentGuard: CanActivateFn = (_route, _state) => {
  return isCateringFirmEnvironment;
};
