import dayjs from 'dayjs/esm';

import { AddressType } from 'app/entities/enumerations/address-type.model';

import { IAddress, NewAddress } from './address.model';

export const sampleWithRequiredData: IAddress = {
  id: 88754,
};

export const sampleWithPartialData: IAddress = {
  id: 45467,
  type: AddressType['PERMANENT_ADDRESS'],
  address1: 'Rubber neural',
  pincode: 'Cape Missouri',
  lattitude: 25141,
  longitude: 2634,
  mapNavUrl: 'neural bottom-line black',
  lastModifiedBy: 'District',
  createdBy: 'Chair viral Soap',
  freeField1: 'bypass United hack',
  freeField2: 'dynamic Birr experiences',
  freeField3: 'bus',
  freefield4: 'Handmade',
};

export const sampleWithFullData: IAddress = {
  id: 26754,
  type: AddressType['PERMANENT_ADDRESS'],
  address1: 'Loan solution',
  hasPrefered: false,
  landMark: 'Island Investment',
  pincode: 'website visionary experiences',
  lattitude: 2622,
  longitude: 3130,
  mapNavUrl: 'infrastructure',
  lastModified: dayjs('2022-09-21T05:16'),
  lastModifiedBy: 'Territories olive SDD',
  createdBy: 'Mouse red',
  createdOn: dayjs('2022-09-21T05:46'),
  isDeleted: true,
  freeField1: 'encryption Circle Ball',
  freeField2: 'Bypass',
  freeField3: 'asynchronous Division Concrete',
  freefield4: 'CSS modular',
  freefield5: 'Grove Brooks hack',
};

export const sampleWithNewData: NewAddress = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
