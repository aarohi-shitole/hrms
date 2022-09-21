import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { IOrganization } from 'app/entities/organization/organization.model';

export interface IBank {
  id: number;
  accountNo?: number | null;
  name?: string | null;
  branch?: string | null;
  ifscCode?: string | null;
  mcirCode?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  employee?: Pick<IEmployee, 'id'> | null;
  organization?: Pick<IOrganization, 'id'> | null;
}

export type NewBank = Omit<IBank, 'id'> & { id: null };
