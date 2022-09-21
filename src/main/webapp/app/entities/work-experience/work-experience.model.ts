import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { CompanyType } from 'app/entities/enumerations/company-type.model';

export interface IWorkExperience {
  id: number;
  jobTitle?: string | null;
  jobDesc?: string | null;
  companyName?: string | null;
  companyType?: CompanyType | null;
  orgAddress?: string | null;
  yearOfExp?: number | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freefield4?: string | null;
  freefield5?: string | null;
  employee?: Pick<IEmployee, 'id'> | null;
}

export type NewWorkExperience = Omit<IWorkExperience, 'id'> & { id: null };
