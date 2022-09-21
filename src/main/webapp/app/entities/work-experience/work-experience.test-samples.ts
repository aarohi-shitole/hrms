import dayjs from 'dayjs/esm';

import { CompanyType } from 'app/entities/enumerations/company-type.model';

import { IWorkExperience, NewWorkExperience } from './work-experience.model';

export const sampleWithRequiredData: IWorkExperience = {
  id: 92187,
};

export const sampleWithPartialData: IWorkExperience = {
  id: 36360,
  jobTitle: 'Global Response Architect',
  jobDesc: 'proactive',
  createdBy: 'Tunisia green Dollar)',
  isDeleted: true,
  freeField1: 'Quality monitor',
  freeField2: 'matrix',
  freeField3: 'Direct BEAC payment',
  freefield4: 'Avon USB Mozambique',
};

export const sampleWithFullData: IWorkExperience = {
  id: 53220,
  jobTitle: 'Corporate Identity Associate',
  jobDesc: 'application',
  companyName: 'Account matrix Streamlined',
  companyType: CompanyType['PROP_INDUSTRY'],
  orgAddress: 'CFP',
  yearOfExp: 14016,
  startDate: dayjs('2022-09-20T17:17'),
  endDate: dayjs('2022-09-20T17:27'),
  lastModified: dayjs('2022-09-21T07:28'),
  lastModifiedBy: 'Money red Small',
  createdBy: 'application withdrawal',
  createdOn: dayjs('2022-09-20T22:18'),
  isDeleted: false,
  freeField1: 'Dam Pound',
  freeField2: 'Multi-tiered Analyst',
  freeField3: 'partnerships Generic',
  freefield4: 'SMS calculating',
  freefield5: 'back Customer Cambridgeshire',
};

export const sampleWithNewData: NewWorkExperience = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
