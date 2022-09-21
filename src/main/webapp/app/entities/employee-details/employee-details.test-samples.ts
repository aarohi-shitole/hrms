import dayjs from 'dayjs/esm';

import { BloodGroup } from 'app/entities/enumerations/blood-group.model';
import { MaritalStatus } from 'app/entities/enumerations/marital-status.model';

import { IEmployeeDetails, NewEmployeeDetails } from './employee-details.model';

export const sampleWithRequiredData: IEmployeeDetails = {
  id: 43079,
};

export const sampleWithPartialData: IEmployeeDetails = {
  id: 51573,
  employeeId: 'Garden Account',
  yearsOfExperience: 4733,
  notes: 'Customer Forks',
  bloodGroup: BloodGroup['A_NEGATIVE'],
  designation: 'tan Loan',
  secondaryContact: 'Solutions indigo',
  hobbies: 'USB Account',
  areaInterest: 'Awesome Haven',
  noOfDependent: 27862,
  languageKnown: 'Kids recontextualize',
  joiningDate: dayjs('2022-09-20T14:42'),
  createdOn: dayjs('2022-09-21T09:19'),
  freefield4: 'invoice didactic',
  freefield5: 'Account Bacon Delaware',
};

export const sampleWithFullData: IEmployeeDetails = {
  id: 28358,
  age: 15335,
  fatherName: 'Chicken grey',
  motherName: 'Iowa',
  employeeId: 'payment Synergized',
  yearsOfExperience: 8530,
  notes: 'Frozen architecture back-end',
  bloodGroup: BloodGroup['AB_POSITIVE'],
  birthDate: 'York Tanzanian dynamic',
  designation: 'Practical',
  expertise: 'protocol Licensed asynchronous',
  jobDescription: 'methodical',
  maritalStatus: MaritalStatus['OTHERS'],
  secondaryContact: 'channels Bike',
  hobbies: 'Metal interfaces',
  areaInterest: 'Station Car',
  noOfDependent: 92724,
  languageKnown: 'Games Bolivia',
  natinality: 'Sleek expedite',
  description: 'teal withdrawal',
  department: 'hard payment Savings',
  joiningDate: dayjs('2022-09-21T02:49'),
  createdBy: 'Assistant',
  createdOn: dayjs('2022-09-20T22:57'),
  freefield1: 'enhance',
  freefield2: 'drive deg strategy',
  freefield3: 'Vermont',
  freefield4: 'Executive',
  freefield5: 'optimizing',
};

export const sampleWithNewData: NewEmployeeDetails = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
