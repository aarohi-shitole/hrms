import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { SeperationStatus } from 'app/entities/enumerations/seperation-status.model';

export interface IEmployeeSeperation {
  id: number;
  reasonForSeperation?: string | null;
  seperationDate?: dayjs.Dayjs | null;
  comment?: string | null;
  seperationStatus?: SeperationStatus | null;
  otherReason?: string | null;
  nagotiatedPeriod?: number | null;
  createdBy?: string | null;
  updatedOn?: dayjs.Dayjs | null;
  createdOn?: dayjs.Dayjs | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freefield1?: string | null;
  freefield2?: string | null;
  freefield3?: string | null;
  freefield4?: string | null;
  freefield5?: string | null;
  employee?: Pick<IEmployee, 'id'> | null;
}

export type NewEmployeeSeperation = Omit<IEmployeeSeperation, 'id'> & { id: null };
