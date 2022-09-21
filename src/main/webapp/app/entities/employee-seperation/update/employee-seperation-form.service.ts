import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEmployeeSeperation, NewEmployeeSeperation } from '../employee-seperation.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmployeeSeperation for edit and NewEmployeeSeperationFormGroupInput for create.
 */
type EmployeeSeperationFormGroupInput = IEmployeeSeperation | PartialWithRequiredKeyOf<NewEmployeeSeperation>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEmployeeSeperation | NewEmployeeSeperation> = Omit<
  T,
  'seperationDate' | 'updatedOn' | 'createdOn' | 'lastModified'
> & {
  seperationDate?: string | null;
  updatedOn?: string | null;
  createdOn?: string | null;
  lastModified?: string | null;
};

type EmployeeSeperationFormRawValue = FormValueOf<IEmployeeSeperation>;

type NewEmployeeSeperationFormRawValue = FormValueOf<NewEmployeeSeperation>;

type EmployeeSeperationFormDefaults = Pick<NewEmployeeSeperation, 'id' | 'seperationDate' | 'updatedOn' | 'createdOn' | 'lastModified'>;

type EmployeeSeperationFormGroupContent = {
  id: FormControl<EmployeeSeperationFormRawValue['id'] | NewEmployeeSeperation['id']>;
  reasonForSeperation: FormControl<EmployeeSeperationFormRawValue['reasonForSeperation']>;
  seperationDate: FormControl<EmployeeSeperationFormRawValue['seperationDate']>;
  comment: FormControl<EmployeeSeperationFormRawValue['comment']>;
  seperationStatus: FormControl<EmployeeSeperationFormRawValue['seperationStatus']>;
  otherReason: FormControl<EmployeeSeperationFormRawValue['otherReason']>;
  nagotiatedPeriod: FormControl<EmployeeSeperationFormRawValue['nagotiatedPeriod']>;
  createdBy: FormControl<EmployeeSeperationFormRawValue['createdBy']>;
  updatedOn: FormControl<EmployeeSeperationFormRawValue['updatedOn']>;
  createdOn: FormControl<EmployeeSeperationFormRawValue['createdOn']>;
  lastModified: FormControl<EmployeeSeperationFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<EmployeeSeperationFormRawValue['lastModifiedBy']>;
  freefield1: FormControl<EmployeeSeperationFormRawValue['freefield1']>;
  freefield2: FormControl<EmployeeSeperationFormRawValue['freefield2']>;
  freefield3: FormControl<EmployeeSeperationFormRawValue['freefield3']>;
  freefield4: FormControl<EmployeeSeperationFormRawValue['freefield4']>;
  freefield5: FormControl<EmployeeSeperationFormRawValue['freefield5']>;
  employee: FormControl<EmployeeSeperationFormRawValue['employee']>;
};

export type EmployeeSeperationFormGroup = FormGroup<EmployeeSeperationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmployeeSeperationFormService {
  createEmployeeSeperationFormGroup(employeeSeperation: EmployeeSeperationFormGroupInput = { id: null }): EmployeeSeperationFormGroup {
    const employeeSeperationRawValue = this.convertEmployeeSeperationToEmployeeSeperationRawValue({
      ...this.getFormDefaults(),
      ...employeeSeperation,
    });
    return new FormGroup<EmployeeSeperationFormGroupContent>({
      id: new FormControl(
        { value: employeeSeperationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      reasonForSeperation: new FormControl(employeeSeperationRawValue.reasonForSeperation),
      seperationDate: new FormControl(employeeSeperationRawValue.seperationDate),
      comment: new FormControl(employeeSeperationRawValue.comment),
      seperationStatus: new FormControl(employeeSeperationRawValue.seperationStatus),
      otherReason: new FormControl(employeeSeperationRawValue.otherReason),
      nagotiatedPeriod: new FormControl(employeeSeperationRawValue.nagotiatedPeriod),
      createdBy: new FormControl(employeeSeperationRawValue.createdBy),
      updatedOn: new FormControl(employeeSeperationRawValue.updatedOn),
      createdOn: new FormControl(employeeSeperationRawValue.createdOn),
      lastModified: new FormControl(employeeSeperationRawValue.lastModified),
      lastModifiedBy: new FormControl(employeeSeperationRawValue.lastModifiedBy),
      freefield1: new FormControl(employeeSeperationRawValue.freefield1),
      freefield2: new FormControl(employeeSeperationRawValue.freefield2),
      freefield3: new FormControl(employeeSeperationRawValue.freefield3),
      freefield4: new FormControl(employeeSeperationRawValue.freefield4),
      freefield5: new FormControl(employeeSeperationRawValue.freefield5),
      employee: new FormControl(employeeSeperationRawValue.employee),
    });
  }

  getEmployeeSeperation(form: EmployeeSeperationFormGroup): IEmployeeSeperation | NewEmployeeSeperation {
    return this.convertEmployeeSeperationRawValueToEmployeeSeperation(
      form.getRawValue() as EmployeeSeperationFormRawValue | NewEmployeeSeperationFormRawValue
    );
  }

  resetForm(form: EmployeeSeperationFormGroup, employeeSeperation: EmployeeSeperationFormGroupInput): void {
    const employeeSeperationRawValue = this.convertEmployeeSeperationToEmployeeSeperationRawValue({
      ...this.getFormDefaults(),
      ...employeeSeperation,
    });
    form.reset(
      {
        ...employeeSeperationRawValue,
        id: { value: employeeSeperationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EmployeeSeperationFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      seperationDate: currentTime,
      updatedOn: currentTime,
      createdOn: currentTime,
      lastModified: currentTime,
    };
  }

  private convertEmployeeSeperationRawValueToEmployeeSeperation(
    rawEmployeeSeperation: EmployeeSeperationFormRawValue | NewEmployeeSeperationFormRawValue
  ): IEmployeeSeperation | NewEmployeeSeperation {
    return {
      ...rawEmployeeSeperation,
      seperationDate: dayjs(rawEmployeeSeperation.seperationDate, DATE_TIME_FORMAT),
      updatedOn: dayjs(rawEmployeeSeperation.updatedOn, DATE_TIME_FORMAT),
      createdOn: dayjs(rawEmployeeSeperation.createdOn, DATE_TIME_FORMAT),
      lastModified: dayjs(rawEmployeeSeperation.lastModified, DATE_TIME_FORMAT),
    };
  }

  private convertEmployeeSeperationToEmployeeSeperationRawValue(
    employeeSeperation: IEmployeeSeperation | (Partial<NewEmployeeSeperation> & EmployeeSeperationFormDefaults)
  ): EmployeeSeperationFormRawValue | PartialWithRequiredKeyOf<NewEmployeeSeperationFormRawValue> {
    return {
      ...employeeSeperation,
      seperationDate: employeeSeperation.seperationDate ? employeeSeperation.seperationDate.format(DATE_TIME_FORMAT) : undefined,
      updatedOn: employeeSeperation.updatedOn ? employeeSeperation.updatedOn.format(DATE_TIME_FORMAT) : undefined,
      createdOn: employeeSeperation.createdOn ? employeeSeperation.createdOn.format(DATE_TIME_FORMAT) : undefined,
      lastModified: employeeSeperation.lastModified ? employeeSeperation.lastModified.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
