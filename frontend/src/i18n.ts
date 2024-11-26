import { OrderStatus } from './app/services/order/order.service';
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
  getOrderStatusName: (status: OrderStatus) => {
    switch (status) {
      case OrderStatus.COMPLETED:
        return 'Completed';
      case OrderStatus.PENDING:
        return 'Pending';
      case OrderStatus.CANCELLED:
        return 'Cancelled';
    }
  },
};
