import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { DependantType } from 'app/entities/enumerations/dependant-type.model';

export interface IDependant {
  id: number;
  name?: string | null;
  age?: string | null;
  dateOfBirth?: string | null;
  type?: DependantType | null;
  mobile?: string | null;
  address?: string | null;
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

export type NewDependant = Omit<IDependant, 'id'> & { id: null };
