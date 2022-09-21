import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEmployee, NewEmployee } from '../employee.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmployee for edit and NewEmployeeFormGroupInput for create.
 */
type EmployeeFormGroupInput = IEmployee | PartialWithRequiredKeyOf<NewEmployee>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEmployee | NewEmployee> = Omit<T, 'resetDate' | 'createdOn'> & {
  resetDate?: string | null;
  createdOn?: string | null;
};

type EmployeeFormRawValue = FormValueOf<IEmployee>;

type NewEmployeeFormRawValue = FormValueOf<NewEmployee>;

type EmployeeFormDefaults = Pick<
  NewEmployee,
  'id' | 'activated' | 'resetDate' | 'createdOn' | 'securityPermissions' | 'securityRoles' | 'organizationPolicies'
>;

type EmployeeFormGroupContent = {
  id: FormControl<EmployeeFormRawValue['id'] | NewEmployee['id']>;
  title: FormControl<EmployeeFormRawValue['title']>;
  firstName: FormControl<EmployeeFormRawValue['firstName']>;
  middleName: FormControl<EmployeeFormRawValue['middleName']>;
  lastName: FormControl<EmployeeFormRawValue['lastName']>;
  grade: FormControl<EmployeeFormRawValue['grade']>;
  username: FormControl<EmployeeFormRawValue['username']>;
  passwordHash: FormControl<EmployeeFormRawValue['passwordHash']>;
  email: FormControl<EmployeeFormRawValue['email']>;
  mobileNo: FormControl<EmployeeFormRawValue['mobileNo']>;
  department: FormControl<EmployeeFormRawValue['department']>;
  imageUrl: FormControl<EmployeeFormRawValue['imageUrl']>;
  activated: FormControl<EmployeeFormRawValue['activated']>;
  langKey: FormControl<EmployeeFormRawValue['langKey']>;
  activationKey: FormControl<EmployeeFormRawValue['activationKey']>;
  resetKey: FormControl<EmployeeFormRawValue['resetKey']>;
  resetDate: FormControl<EmployeeFormRawValue['resetDate']>;
  createdBy: FormControl<EmployeeFormRawValue['createdBy']>;
  createdOn: FormControl<EmployeeFormRawValue['createdOn']>;
  employeeDetails: FormControl<EmployeeFormRawValue['employeeDetails']>;
  organization: FormControl<EmployeeFormRawValue['organization']>;
  incomeTaxSlab: FormControl<EmployeeFormRawValue['incomeTaxSlab']>;
  securityPermissions: FormControl<EmployeeFormRawValue['securityPermissions']>;
  securityRoles: FormControl<EmployeeFormRawValue['securityRoles']>;
  organizationPolicies: FormControl<EmployeeFormRawValue['organizationPolicies']>;
};

export type EmployeeFormGroup = FormGroup<EmployeeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmployeeFormService {
  createEmployeeFormGroup(employee: EmployeeFormGroupInput = { id: null }): EmployeeFormGroup {
    const employeeRawValue = this.convertEmployeeToEmployeeRawValue({
      ...this.getFormDefaults(),
      ...employee,
    });
    return new FormGroup<EmployeeFormGroupContent>({
      id: new FormControl(
        { value: employeeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      title: new FormControl(employeeRawValue.title),
      firstName: new FormControl(employeeRawValue.firstName),
      middleName: new FormControl(employeeRawValue.middleName),
      lastName: new FormControl(employeeRawValue.lastName),
      grade: new FormControl(employeeRawValue.grade),
      username: new FormControl(employeeRawValue.username, {
        validators: [Validators.required],
      }),
      passwordHash: new FormControl(employeeRawValue.passwordHash, {
        validators: [Validators.required],
      }),
      email: new FormControl(employeeRawValue.email),
      mobileNo: new FormControl(employeeRawValue.mobileNo),
      department: new FormControl(employeeRawValue.department),
      imageUrl: new FormControl(employeeRawValue.imageUrl),
      activated: new FormControl(employeeRawValue.activated),
      langKey: new FormControl(employeeRawValue.langKey),
      activationKey: new FormControl(employeeRawValue.activationKey),
      resetKey: new FormControl(employeeRawValue.resetKey),
      resetDate: new FormControl(employeeRawValue.resetDate),
      createdBy: new FormControl(employeeRawValue.createdBy),
      createdOn: new FormControl(employeeRawValue.createdOn),
      employeeDetails: new FormControl(employeeRawValue.employeeDetails),
      organization: new FormControl(employeeRawValue.organization),
      incomeTaxSlab: new FormControl(employeeRawValue.incomeTaxSlab),
      securityPermissions: new FormControl(employeeRawValue.securityPermissions ?? []),
      securityRoles: new FormControl(employeeRawValue.securityRoles ?? []),
      organizationPolicies: new FormControl(employeeRawValue.organizationPolicies ?? []),
    });
  }

  getEmployee(form: EmployeeFormGroup): IEmployee | NewEmployee {
    return this.convertEmployeeRawValueToEmployee(form.getRawValue() as EmployeeFormRawValue | NewEmployeeFormRawValue);
  }

  resetForm(form: EmployeeFormGroup, employee: EmployeeFormGroupInput): void {
    const employeeRawValue = this.convertEmployeeToEmployeeRawValue({ ...this.getFormDefaults(), ...employee });
    form.reset(
      {
        ...employeeRawValue,
        id: { value: employeeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EmployeeFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      activated: false,
      resetDate: currentTime,
      createdOn: currentTime,
      securityPermissions: [],
      securityRoles: [],
      organizationPolicies: [],
    };
  }

  private convertEmployeeRawValueToEmployee(rawEmployee: EmployeeFormRawValue | NewEmployeeFormRawValue): IEmployee | NewEmployee {
    return {
      ...rawEmployee,
      resetDate: dayjs(rawEmployee.resetDate, DATE_TIME_FORMAT),
      createdOn: dayjs(rawEmployee.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertEmployeeToEmployeeRawValue(
    employee: IEmployee | (Partial<NewEmployee> & EmployeeFormDefaults)
  ): EmployeeFormRawValue | PartialWithRequiredKeyOf<NewEmployeeFormRawValue> {
    return {
      ...employee,
      resetDate: employee.resetDate ? employee.resetDate.format(DATE_TIME_FORMAT) : undefined,
      createdOn: employee.createdOn ? employee.createdOn.format(DATE_TIME_FORMAT) : undefined,
      securityPermissions: employee.securityPermissions ?? [],
      securityRoles: employee.securityRoles ?? [],
      organizationPolicies: employee.organizationPolicies ?? [],
    };
  }
}
