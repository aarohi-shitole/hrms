import dayjs from 'dayjs/esm';
import { ISecurityRole } from 'app/entities/security-role/security-role.model';
import { IEmployee } from 'app/entities/employee/employee.model';

export interface ISecurityPermission {
  id: number;
  permissionName?: string | null;
  description?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  securityRoles?: Pick<ISecurityRole, 'id' | 'roleName'>[] | null;
  employees?: Pick<IEmployee, 'id' | 'username'>[] | null;
}

export type NewSecurityPermission = Omit<ISecurityPermission, 'id'> & { id: null };
