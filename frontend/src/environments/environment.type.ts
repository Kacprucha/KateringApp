export interface Environment {
  production: boolean;
  apiUrl: string;
  environmentName: 'development' | 'catering-firm' | 'client';
  clientUiUrl: string;
  cateringFirmUiUrl: string;
}
