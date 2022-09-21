import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEmployeeDetails, NewEmployeeDetails } from '../employee-details.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmployeeDetails for edit and NewEmployeeDetailsFormGroupInput for create.
 */
type EmployeeDetailsFormGroupInput = IEmployeeDetails | PartialWithRequiredKeyOf<NewEmployeeDetails>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEmployeeDetails | NewEmployeeDetails> = Omit<T, 'joiningDate' | 'createdOn'> & {
  joiningDate?: string | null;
  createdOn?: string | null;
};

type EmployeeDetailsFormRawValue = FormValueOf<IEmployeeDetails>;

type NewEmployeeDetailsFormRawValue = FormValueOf<NewEmployeeDetails>;

type EmployeeDetailsFormDefaults = Pick<NewEmployeeDetails, 'id' | 'joiningDate' | 'createdOn'>;

type EmployeeDetailsFormGroupContent = {
  id: FormControl<EmployeeDetailsFormRawValue['id'] | NewEmployeeDetails['id']>;
  age: FormControl<EmployeeDetailsFormRawValue['age']>;
  fatherName: FormControl<EmployeeDetailsFormRawValue['fatherName']>;
  motherName: FormControl<EmployeeDetailsFormRawValue['motherName']>;
  employeeId: FormControl<EmployeeDetailsFormRawValue['employeeId']>;
  yearsOfExperience: FormControl<EmployeeDetailsFormRawValue['yearsOfExperience']>;
  notes: FormControl<EmployeeDetailsFormRawValue['notes']>;
  bloodGroup: FormControl<EmployeeDetailsFormRawValue['bloodGroup']>;
  birthDate: FormControl<EmployeeDetailsFormRawValue['birthDate']>;
  designation: FormControl<EmployeeDetailsFormRawValue['designation']>;
  expertise: FormControl<EmployeeDetailsFormRawValue['expertise']>;
  jobDescription: FormControl<EmployeeDetailsFormRawValue['jobDescription']>;
  maritalStatus: FormControl<EmployeeDetailsFormRawValue['maritalStatus']>;
  secondaryContact: FormControl<EmployeeDetailsFormRawValue['secondaryContact']>;
  hobbies: FormControl<EmployeeDetailsFormRawValue['hobbies']>;
  areaInterest: FormControl<EmployeeDetailsFormRawValue['areaInterest']>;
  noOfDependent: FormControl<EmployeeDetailsFormRawValue['noOfDependent']>;
  languageKnown: FormControl<EmployeeDetailsFormRawValue['languageKnown']>;
  natinality: FormControl<EmployeeDetailsFormRawValue['natinality']>;
  description: FormControl<EmployeeDetailsFormRawValue['description']>;
  department: FormControl<EmployeeDetailsFormRawValue['department']>;
  joiningDate: FormControl<EmployeeDetailsFormRawValue['joiningDate']>;
  createdBy: FormControl<EmployeeDetailsFormRawValue['createdBy']>;
  createdOn: FormControl<EmployeeDetailsFormRawValue['createdOn']>;
  freefield1: FormControl<EmployeeDetailsFormRawValue['freefield1']>;
  freefield2: FormControl<EmployeeDetailsFormRawValue['freefield2']>;
  freefield3: FormControl<EmployeeDetailsFormRawValue['freefield3']>;
  freefield4: FormControl<EmployeeDetailsFormRawValue['freefield4']>;
  freefield5: FormControl<EmployeeDetailsFormRawValue['freefield5']>;
};

export type EmployeeDetailsFormGroup = FormGroup<EmployeeDetailsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmployeeDetailsFormService {
  createEmployeeDetailsFormGroup(employeeDetails: EmployeeDetailsFormGroupInput = { id: null }): EmployeeDetailsFormGroup {
    const employeeDetailsRawValue = this.convertEmployeeDetailsToEmployeeDetailsRawValue({
      ...this.getFormDefaults(),
      ...employeeDetails,
    });
    return new FormGroup<EmployeeDetailsFormGroupContent>({
      id: new FormControl(
        { value: employeeDetailsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      age: new FormControl(employeeDetailsRawValue.age),
      fatherName: new FormControl(employeeDetailsRawValue.fatherName),
      motherName: new FormControl(employeeDetailsRawValue.motherName),
      employeeId: new FormControl(employeeDetailsRawValue.employeeId),
      yearsOfExperience: new FormControl(employeeDetailsRawValue.yearsOfExperience),
      notes: new FormControl(employeeDetailsRawValue.notes),
      bloodGroup: new FormControl(employeeDetailsRawValue.bloodGroup),
      birthDate: new FormControl(employeeDetailsRawValue.birthDate),
      designation: new FormControl(employeeDetailsRawValue.designation),
      expertise: new FormControl(employeeDetailsRawValue.expertise),
      jobDescription: new FormControl(employeeDetailsRawValue.jobDescription),
      maritalStatus: new FormControl(employeeDetailsRawValue.maritalStatus),
      secondaryContact: new FormControl(employeeDetailsRawValue.secondaryContact),
      hobbies: new FormControl(employeeDetailsRawValue.hobbies),
      areaInterest: new FormControl(employeeDetailsRawValue.areaInterest),
      noOfDependent: new FormControl(employeeDetailsRawValue.noOfDependent),
      languageKnown: new FormControl(employeeDetailsRawValue.languageKnown),
      natinality: new FormControl(employeeDetailsRawValue.natinality),
      description: new FormControl(employeeDetailsRawValue.description),
      department: new FormControl(employeeDetailsRawValue.department),
      joiningDate: new FormControl(employeeDetailsRawValue.joiningDate),
      createdBy: new FormControl(employeeDetailsRawValue.createdBy),
      createdOn: new FormControl(employeeDetailsRawValue.createdOn),
      freefield1: new FormControl(employeeDetailsRawValue.freefield1),
      freefield2: new FormControl(employeeDetailsRawValue.freefield2),
      freefield3: new FormControl(employeeDetailsRawValue.freefield3),
      freefield4: new FormControl(employeeDetailsRawValue.freefield4),
      freefield5: new FormControl(employeeDetailsRawValue.freefield5),
    });
  }

  getEmployeeDetails(form: EmployeeDetailsFormGroup): IEmployeeDetails | NewEmployeeDetails {
    return this.convertEmployeeDetailsRawValueToEmployeeDetails(
      form.getRawValue() as EmployeeDetailsFormRawValue | NewEmployeeDetailsFormRawValue
    );
  }

  resetForm(form: EmployeeDetailsFormGroup, employeeDetails: EmployeeDetailsFormGroupInput): void {
    const employeeDetailsRawValue = this.convertEmployeeDetailsToEmployeeDetailsRawValue({ ...this.getFormDefaults(), ...employeeDetails });
    form.reset(
      {
        ...employeeDetailsRawValue,
        id: { value: employeeDetailsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EmployeeDetailsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      joiningDate: currentTime,
      createdOn: currentTime,
    };
  }

  private convertEmployeeDetailsRawValueToEmployeeDetails(
    rawEmployeeDetails: EmployeeDetailsFormRawValue | NewEmployeeDetailsFormRawValue
  ): IEmployeeDetails | NewEmployeeDetails {
    return {
      ...rawEmployeeDetails,
      joiningDate: dayjs(rawEmployeeDetails.joiningDate, DATE_TIME_FORMAT),
      createdOn: dayjs(rawEmployeeDetails.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertEmployeeDetailsToEmployeeDetailsRawValue(
    employeeDetails: IEmployeeDetails | (Partial<NewEmployeeDetails> & EmployeeDetailsFormDefaults)
  ): EmployeeDetailsFormRawValue | PartialWithRequiredKeyOf<NewEmployeeDetailsFormRawValue> {
    return {
      ...employeeDetails,
      joiningDate: employeeDetails.joiningDate ? employeeDetails.joiningDate.format(DATE_TIME_FORMAT) : undefined,
      createdOn: employeeDetails.createdOn ? employeeDetails.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
