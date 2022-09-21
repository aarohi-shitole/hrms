import dayjs from 'dayjs/esm';
import { IEmployeeDetails } from 'app/entities/employee-details/employee-details.model';
import { IOrganization } from 'app/entities/organization/organization.model';
import { IIncomeTaxSlab } from 'app/entities/income-tax-slab/income-tax-slab.model';
import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';
import { ISecurityRole } from 'app/entities/security-role/security-role.model';
import { IOrganizationPolicies } from 'app/entities/organization-policies/organization-policies.model';
import { Title } from 'app/entities/enumerations/title.model';

export interface IEmployee {
  id: number;
  title?: Title | null;
  firstName?: string | null;
  middleName?: string | null;
  lastName?: string | null;
  grade?: string | null;
  username?: string | null;
  passwordHash?: string | null;
  email?: string | null;
  mobileNo?: string | null;
  department?: string | null;
  imageUrl?: string | null;
  activated?: boolean | null;
  langKey?: string | null;
  activationKey?: string | null;
  resetKey?: string | null;
  resetDate?: dayjs.Dayjs | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  employeeDetails?: Pick<IEmployeeDetails, 'id'> | null;
  organization?: Pick<IOrganization, 'id'> | null;
  incomeTaxSlab?: Pick<IIncomeTaxSlab, 'id'> | null;
  securityPermissions?: Pick<ISecurityPermission, 'id' | 'permissionName'>[] | null;
  securityRoles?: Pick<ISecurityRole, 'id' | 'roleName'>[] | null;
  organizationPolicies?: Pick<IOrganizationPolicies, 'id'>[] | null;
}

export type NewEmployee = Omit<IEmployee, 'id'> & { id: null };
