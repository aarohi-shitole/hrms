import dayjs from 'dayjs/esm';
import { AddressType } from 'app/entities/enumerations/address-type.model';

export interface IAddress {
  id: number;
  type?: AddressType | null;
  address1?: string | null;
  hasPrefered?: boolean | null;
  landMark?: string | null;
  pincode?: string | null;
  lattitude?: number | null;
  longitude?: number | null;
  mapNavUrl?: string | null;
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
}

export type NewAddress = Omit<IAddress, 'id'> & { id: null };
