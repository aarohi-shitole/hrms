import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { Answers } from 'app/entities/enumerations/answers.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface IExitFormalties {
  id: number;
  security?: Answers | null;
  feedback?: string | null;
  exitDate?: dayjs.Dayjs | null;
  exitInterview?: Answers | null;
  libNoDue?: Answers | null;
  noticePeriodServed?: Answers | null;
  clearence?: Answers | null;
  orgAssets?: Answers | null;
  orgVehical?: Answers | null;
  resignLetter?: Answers | null;
  shares?: string | null;
  staffWelfare?: string | null;
  workForOrg?: Answers | null;
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
