import dayjs from 'dayjs/esm';
import { IAddress } from 'app/entities/address/address.model';
import { OrganizationType } from 'app/entities/enumerations/organization-type.model';
import { Status } from 'app/entities/enumerations/status.model';

export interface IOrganization {
  id: number;
  orgName?: string | null;
  branchcode?: string | null;
  region?: string | null;
  ifscCode?: string | null;
  micrCode?: string | null;
  swiftCode?: string | null;
  ibanCode?: string | null;
  routingTransitNo?: string | null;
  regNumber?: number | null;
  gstinNumber?: string | null;
  panNumber?: string | null;
  tanNumber?: string | null;
  phone?: string | null;
  email?: string | null;
  webSite?: string | null;
  fax?: string | null;
  orgType?: OrganizationType | null;
  createdOn?: dayjs.Dayjs | null;
  createdBy?: string | null;
  description?: string | null;
  isDeleted?: string | null;
  status?: Status | null;
  lastModified?: dayjs.Dayjs | null;
  lastModifiedBy?: string | null;
  freeField1?: string | null;
  freeField2?: string | null;
  freeField3?: string | null;
  freeField4?: string | null;
  address?: Pick<IAddress, 'id'> | null;
  organization?: Pick<IOrganization, 'id'> | null;
}

export type NewOrganization = Omit<IOrganization, 'id'> & { id: null };
