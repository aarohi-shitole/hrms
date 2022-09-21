import dayjs from 'dayjs/esm';

import { Degree } from 'app/entities/enumerations/degree.model';

import { IEducation, NewEducation } from './education.model';

export const sampleWithRequiredData: IEducation = {
  id: 81904,
};

export const sampleWithPartialData: IEducation = {
  id: 9085,
  schoolName: 'leverage Concrete Bedfordshire',
  eduType: 'whiteboard Licensed leverage',
  grade: 26257,
  createdOn: dayjs('2022-09-21T03:17'),
  isDeleted: true,
  freeField2: 'Ergonomic',
};

export const sampleWithFullData: IEducation = {
  id: 62540,
  schoolName: 'Assistant Gloves',
  degree: Degree['GRADUATION'],
  sector: 'circuit bluetooth Wooden',
  eduType: 'Producer',
  startDate: dayjs('2022-09-21T07:24'),
  endDate: dayjs('2022-09-20T10:32'),
  grade: 83337,
  description: 'pixel Chips deposit',
  lastModified: dayjs('2022-09-21T02:35'),
  lastModifiedBy: 'multi-byte portals Checking',
  createdBy: 'Plaza maroon',
  createdOn: dayjs('2022-09-20T09:58'),
  isDeleted: false,
  freeField1: 'Mountains Health Hat',
  freeField2: 'cross-platform bluetooth world-class',
  freeField3: 'Computers Handcrafted',
};

export const sampleWithNewData: NewEducation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
