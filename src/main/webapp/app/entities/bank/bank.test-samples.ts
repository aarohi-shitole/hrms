import dayjs from 'dayjs/esm';

import { IBank, NewBank } from './bank.model';

export const sampleWithRequiredData: IBank = {
  id: 40079,
};

export const sampleWithPartialData: IBank = {
  id: 44179,
  accountNo: 22896,
  branch: 'deliver Cambridgeshire',
  ifscCode: 'optical seize',
  freeField3: 'Mountain Frozen',
};

export const sampleWithFullData: IBank = {
  id: 70203,
  accountNo: 83779,
  name: 'ROI Dakota',
  branch: '1080p Home',
  ifscCode: 'database Idaho',
  mcirCode: 'Industrial deposit',
  lastModified: dayjs('2022-09-21T02:36'),
  lastModifiedBy: 'Madagascar',
  createdBy: 'Iraqi Multi-channelled',
  createdOn: dayjs('2022-09-21T05:45'),
  isDeleted: true,
  freeField1: 'deliver Cuba',
  freeField2: 'Island Associate alarm',
  freeField3: 'neural Shore',
};

export const sampleWithNewData: NewBank = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
