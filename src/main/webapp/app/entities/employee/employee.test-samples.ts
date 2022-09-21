import dayjs from 'dayjs/esm';

import { Title } from 'app/entities/enumerations/title.model';

import { IEmployee, NewEmployee } from './employee.model';

export const sampleWithRequiredData: IEmployee = {
  id: 7813,
  username: 'transmitting',
  passwordHash: 'scale Mexican Administrator',
};

export const sampleWithPartialData: IEmployee = {
  id: 36931,
  middleName: 'SSL',
  lastName: 'Raynor',
  username: 'Factors world-class',
  passwordHash: 'Berkshire Identity',
  mobileNo: 'Rubber Fundamental Loan',
  activated: false,
  activationKey: 'Card',
  createdBy: 'embrace uniform',
};

export const sampleWithFullData: IEmployee = {
  id: 52105,
  title: Title['MRS'],
  firstName: 'Paolo',
  middleName: 'payment exuding',
  lastName: 'Carroll',
  grade: 'Divide',
  username: 'Account Lesotho Bhutanese',
  passwordHash: 'Tactics',
  email: 'Mariane21@gmail.com',
  mobileNo: 'Account',
  department: 'Senior Group Krone',
  imageUrl: 'Brand Crossroad',
  activated: true,
  langKey: 'Money circuit Manager',
  activationKey: 'Home EXE Points',
  resetKey: 'program',
  resetDate: dayjs('2022-09-21T02:38'),
  createdBy: 'synergistic Product Botswana',
  createdOn: dayjs('2022-09-20T20:00'),
};

export const sampleWithNewData: NewEmployee = {
  username: 'withdrawal transmitter port',
  passwordHash: 'Refined',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
