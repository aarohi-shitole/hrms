import dayjs from 'dayjs/esm';

import { Answers } from 'app/entities/enumerations/answers.model';
import { Status } from 'app/entities/enumerations/status.model';

import { IExitFormalties, NewExitFormalties } from './exit-formalties.model';

export const sampleWithRequiredData: IExitFormalties = {
  id: 91008,
};

export const sampleWithPartialData: IExitFormalties = {
  id: 13201,
  security: Answers['NO'],
  feedback: 'compress Intelligent connecting',
  exitDate: dayjs('2022-09-21T01:00'),
  noticePeriodServed: Answers['YES'],
  clearence: Answers['NO'],
  orgAssets: Answers['NO'],
  resignLetter: Answers['NO'],
  shares: 'Cotton',
  workForOrg: Answers['YES'],
  freeField2: 'port Checking Future-proofed',
  freeField3: 'bandwidth quantifying Programmable',
  freefield5: 'Chief',
};

export const sampleWithFullData: IExitFormalties = {
  id: 83964,
  security: Answers['YES'],
  feedback: 'Sleek Louisiana',
  exitDate: dayjs('2022-09-21T08:02'),
  exitInterview: Answers['NO'],
  libNoDue: Answers['YES'],
  noticePeriodServed: Answers['NO'],
  clearence: Answers['YES'],
  orgAssets: Answers['NO'],
  orgVehical: Answers['YES'],
  resignLetter: Answers['YES'],
  shares: 'Cross-group capacitor Agent',
  staffWelfare: 'Account Loan plum',
  workForOrg: Answers['NO'],
  status: Status['INACTIVE'],
  lastModified: dayjs('2022-09-21T06:08'),
  lastModifiedBy: 'Savings',
  createdBy: 'Web bypass Front-line',
  createdOn: dayjs('2022-09-20T14:13'),
  isDeleted: false,
  freeField1: 'Fish',
  freeField2: 'green Fresh',
  freeField3: 'invoice Bedfordshire',
  freefield4: 'Way',
  freefield5: 'Handcrafted Peso',
};

export const sampleWithNewData: NewExitFormalties = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
