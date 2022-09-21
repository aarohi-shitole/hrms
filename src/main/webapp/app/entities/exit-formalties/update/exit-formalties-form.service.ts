import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IExitFormalties, NewExitFormalties } from '../exit-formalties.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IExitFormalties for edit and NewExitFormaltiesFormGroupInput for create.
 */
type ExitFormaltiesFormGroupInput = IExitFormalties | PartialWithRequiredKeyOf<NewExitFormalties>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IExitFormalties | NewExitFormalties> = Omit<T, 'exitDate' | 'lastModified' | 'createdOn'> & {
  exitDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

type ExitFormaltiesFormRawValue = FormValueOf<IExitFormalties>;

type NewExitFormaltiesFormRawValue = FormValueOf<NewExitFormalties>;

type ExitFormaltiesFormDefaults = Pick<NewExitFormalties, 'id' | 'exitDate' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type ExitFormaltiesFormGroupContent = {
  id: FormControl<ExitFormaltiesFormRawValue['id'] | NewExitFormalties['id']>;
  security: FormControl<ExitFormaltiesFormRawValue['security']>;
  feedback: FormControl<ExitFormaltiesFormRawValue['feedback']>;
  exitDate: FormControl<ExitFormaltiesFormRawValue['exitDate']>;
  exitInterview: FormControl<ExitFormaltiesFormRawValue['exitInterview']>;
  libNoDue: FormControl<ExitFormaltiesFormRawValue['libNoDue']>;
  noticePeriodServed: FormControl<ExitFormaltiesFormRawValue['noticePeriodServed']>;
  clearence: FormControl<ExitFormaltiesFormRawValue['clearence']>;
  orgAssets: FormControl<ExitFormaltiesFormRawValue['orgAssets']>;
  orgVehical: FormControl<ExitFormaltiesFormRawValue['orgVehical']>;
  resignLetter: FormControl<ExitFormaltiesFormRawValue['resignLetter']>;
  shares: FormControl<ExitFormaltiesFormRawValue['shares']>;
  staffWelfare: FormControl<ExitFormaltiesFormRawValue['staffWelfare']>;
  workForOrg: FormControl<ExitFormaltiesFormRawValue['workForOrg']>;
  status: FormControl<ExitFormaltiesFormRawValue['status']>;
  lastModified: FormControl<ExitFormaltiesFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<ExitFormaltiesFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<ExitFormaltiesFormRawValue['createdBy']>;
  createdOn: FormControl<ExitFormaltiesFormRawValue['createdOn']>;
  isDeleted: FormControl<ExitFormaltiesFormRawValue['isDeleted']>;
  freeField1: FormControl<ExitFormaltiesFormRawValue['freeField1']>;
  freeField2: FormControl<ExitFormaltiesFormRawValue['freeField2']>;
  freeField3: FormControl<ExitFormaltiesFormRawValue['freeField3']>;
  freefield4: FormControl<ExitFormaltiesFormRawValue['freefield4']>;
  freefield5: FormControl<ExitFormaltiesFormRawValue['freefield5']>;
  employee: FormControl<ExitFormaltiesFormRawValue['employee']>;
};

export type ExitFormaltiesFormGroup = FormGroup<ExitFormaltiesFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ExitFormaltiesFormService {
  createExitFormaltiesFormGroup(exitFormalties: ExitFormaltiesFormGroupInput = { id: null }): ExitFormaltiesFormGroup {
    const exitFormaltiesRawValue = this.convertExitFormaltiesToExitFormaltiesRawValue({
      ...this.getFormDefaults(),
      ...exitFormalties,
    });
    return new FormGroup<ExitFormaltiesFormGroupContent>({
      id: new FormControl(
        { value: exitFormaltiesRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      security: new FormControl(exitFormaltiesRawValue.security),
      feedback: new FormControl(exitFormaltiesRawValue.feedback),
      exitDate: new FormControl(exitFormaltiesRawValue.exitDate),
      exitInterview: new FormControl(exitFormaltiesRawValue.exitInterview),
      libNoDue: new FormControl(exitFormaltiesRawValue.libNoDue),
      noticePeriodServed: new FormControl(exitFormaltiesRawValue.noticePeriodServed),
      clearence: new FormControl(exitFormaltiesRawValue.clearence),
      orgAssets: new FormControl(exitFormaltiesRawValue.orgAssets),
      orgVehical: new FormControl(exitFormaltiesRawValue.orgVehical),
      resignLetter: new FormControl(exitFormaltiesRawValue.resignLetter),
      shares: new FormControl(exitFormaltiesRawValue.shares),
      staffWelfare: new FormControl(exitFormaltiesRawValue.staffWelfare),
      workForOrg: new FormControl(exitFormaltiesRawValue.workForOrg),
      status: new FormControl(exitFormaltiesRawValue.status),
      lastModified: new FormControl(exitFormaltiesRawValue.lastModified),
      lastModifiedBy: new FormControl(exitFormaltiesRawValue.lastModifiedBy),
      createdBy: new FormControl(exitFormaltiesRawValue.createdBy),
      createdOn: new FormControl(exitFormaltiesRawValue.createdOn),
      isDeleted: new FormControl(exitFormaltiesRawValue.isDeleted),
      freeField1: new FormControl(exitFormaltiesRawValue.freeField1),
      freeField2: new FormControl(exitFormaltiesRawValue.freeField2),
      freeField3: new FormControl(exitFormaltiesRawValue.freeField3),
      freefield4: new FormControl(exitFormaltiesRawValue.freefield4),
      freefield5: new FormControl(exitFormaltiesRawValue.freefield5),
      employee: new FormControl(exitFormaltiesRawValue.employee),
    });
  }

  getExitFormalties(form: ExitFormaltiesFormGroup): IExitFormalties | NewExitFormalties {
    return this.convertExitFormaltiesRawValueToExitFormalties(
      form.getRawValue() as ExitFormaltiesFormRawValue | NewExitFormaltiesFormRawValue
    );
  }

  resetForm(form: ExitFormaltiesFormGroup, exitFormalties: ExitFormaltiesFormGroupInput): void {
    const exitFormaltiesRawValue = this.convertExitFormaltiesToExitFormaltiesRawValue({ ...this.getFormDefaults(), ...exitFormalties });
    form.reset(
      {
        ...exitFormaltiesRawValue,
        id: { value: exitFormaltiesRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): ExitFormaltiesFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      exitDate: currentTime,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertExitFormaltiesRawValueToExitFormalties(
    rawExitFormalties: ExitFormaltiesFormRawValue | NewExitFormaltiesFormRawValue
  ): IExitFormalties | NewExitFormalties {
    return {
      ...rawExitFormalties,
      exitDate: dayjs(rawExitFormalties.exitDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawExitFormalties.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawExitFormalties.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertExitFormaltiesToExitFormaltiesRawValue(
    exitFormalties: IExitFormalties | (Partial<NewExitFormalties> & ExitFormaltiesFormDefaults)
  ): ExitFormaltiesFormRawValue | PartialWithRequiredKeyOf<NewExitFormaltiesFormRawValue> {
    return {
      ...exitFormalties,
      exitDate: exitFormalties.exitDate ? exitFormalties.exitDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: exitFormalties.lastModified ? exitFormalties.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: exitFormalties.createdOn ? exitFormalties.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
