import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';
import { IOrganization } from 'app/entities/organization/organization.model';

export interface IDocuments {
  id: number;
  referenceId?: number | null;
  type?: string | null;
  subtype?: string | null;
  fileName?: string | null;
  filePath?: string | null;
  fileUrl?: string | null;
  issuedDate?: dayjs.Dayjs | null;
  validUpTo?: dayjs.Dayjs | null;
  placeOfIssued?: string | null;
  hasVerified?: boolean | null;
  remark?: string | null;
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

export type NewDocuments = Omit<IDocuments, 'id'> & { id: null };
