import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { Options } from 'app/entities/enumerations/options.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface IExitFormalties {
  id: number;
  security?: Options | null;
  feedback?: string | null;
  exitDate?: dayjs.Dayjs | null;
  exitInterview?: Options | null;
  libNoDue?: Options | null;
  noticePeriodServed?: Options | null;
  clearence?: Options | null;
  orgAssets?: Options | null;
  orgVehical?: Options | null;
  resignLetter?: Options | null;
  shares?: string | null;
  staffWelfare?: string | null;
  workForOrg?: Options | null;
  status?: Status | null;
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

export type NewExitFormalties = Omit<IExitFormalties, 'id'> & { id: null };
