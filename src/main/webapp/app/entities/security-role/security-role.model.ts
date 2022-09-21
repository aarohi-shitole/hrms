import dayjs from 'dayjs/esm';
import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';
import { IEmployee } from 'app/entities/employee/employee.model';

export interface ISecurityRole {
  id: number;
  roleName?: string | null;
  description?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  securityPermissions?: Pick<ISecurityPermission, 'id' | 'permissionName'>[] | null;
  employees?: Pick<IEmployee, 'id' | 'username'>[] | null;
}

export type NewSecurityRole = Omit<ISecurityRole, 'id'> & { id: null };
