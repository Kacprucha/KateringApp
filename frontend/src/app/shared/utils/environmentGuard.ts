import { environment } from '../../../environments/environment';

export const isCateringFirmEnvironment =
  environment.environmentName === 'development' ||
  environment.environmentName === 'catering-firm';

export const isClientEnvironment =
  environment.environmentName === 'development' ||
  environment.environmentName === 'client';
