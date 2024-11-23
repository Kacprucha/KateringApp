type EnvironmentName = 'development' | 'catering-firm' | 'client';

export interface Environment {
  production: boolean;
  apiUrl: string;
  environmentName: EnvironmentName;
}
