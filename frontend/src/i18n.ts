import { UserRole } from './app/types/user-roles';

export const i18n = {
  getUserRoleName: (role: UserRole) => {
    switch (role) {
      case UserRole.CateringFirm:
        return 'Catering Firm';
      case UserRole.Client:
        return 'Client';
    }
  },
};
