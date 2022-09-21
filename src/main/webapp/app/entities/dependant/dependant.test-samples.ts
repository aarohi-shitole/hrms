import dayjs from 'dayjs/esm';

import { DependantType } from 'app/entities/enumerations/dependant-type.model';

import { IDependant, NewDependant } from './dependant.model';

export const sampleWithRequiredData: IDependant = {
  id: 76848,
};

export const sampleWithPartialData: IDependant = {
  id: 37085,
  name: 'foreground',
  type: DependantType['SISTER'],
  mobile: 'whiteboard',
  isDeleted: true,
  freeField1: 'COM Berkshire Salad',
  freeField3: 'backing',
  freefield5: 'Metal SMTP',
};

export const sampleWithFullData: IDependant = {
  id: 54119,
  name: 'Incredible connecting',
  age: 'invoice SMTP',
  dateOfBirth: 'Computer',
  type: DependantType['SPOUSE'],
  mobile: 'CSS',
  address: 'primary overriding',
  lastModified: dayjs('2022-09-21T06:39'),
  lastModifiedBy: 'exuding projection deploy',
  createdBy: 'Fantastic Iowa Shoals',
  createdOn: dayjs('2022-09-20T09:55'),
  isDeleted: false,
  freeField1: 'Cheese overriding',
  freeField2: 'optical client-server',
  freeField3: 'database web-readiness',
  freefield4: 'Dinar Wooden',
  freefield5: 'Cotton',
};

export const sampleWithNewData: NewDependant = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
