import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { Degree } from 'app/entities/enumerations/degree.model';

export interface IEducation {
  id: number;
  schoolName?: string | null;
  degree?: Degree | null;
  sector?: string | null;
  eduType?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  grade?: number | null;
  description?: string | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  isDeleted?: boolean | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  employee?: Pick<IEmployee, 'id'> | null;
}

export type NewEducation = Omit<IEducation, 'id'> & { id: null };
