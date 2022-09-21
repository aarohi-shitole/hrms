import dayjs from 'dayjs/esm';
import { BloodGroup } from 'app/entities/enumerations/blood-group.model';
import { MaritalStatus } from 'app/entities/enumerations/marital-status.model';

export interface IEmployeeDetails {
  id: number;
  age?: number | null;
  fatherName?: string | null;
  motherName?: string | null;
  employeeId?: string | null;
  yearsOfExperience?: number | null;
  notes?: string | null;
  bloodGroup?: BloodGroup | null;
  birthDate?: string | null;
  designation?: string | null;
  expertise?: string | null;
  jobDescription?: string | null;
  maritalStatus?: MaritalStatus | null;
  secondaryContact?: string | null;
  hobbies?: string | null;
  areaInterest?: string | null;
  noOfDependent?: number | null;
  languageKnown?: string | null;
  natinality?: string | null;
  description?: string | null;
  department?: string | null;
  joiningDate?: dayjs.Dayjs | null;
  createdBy?: string | null;
  createdOn?: dayjs.Dayjs | null;
  freefield1?: string | null;
  freefield2?: string | null;
  freefield3?: string | null;
  freefield4?: string | null;
  freefield5?: string | null;
}

export type NewEmployeeDetails = Omit<IEmployeeDetails, 'id'> & { id: null };
