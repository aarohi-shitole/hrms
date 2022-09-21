import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IWorkExperience, NewWorkExperience } from '../work-experience.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IWorkExperience for edit and NewWorkExperienceFormGroupInput for create.
 */
type WorkExperienceFormGroupInput = IWorkExperience | PartialWithRequiredKeyOf<NewWorkExperience>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IWorkExperience | NewWorkExperience> = Omit<T, 'startDate' | 'endDate' | 'lastModified' | 'createdOn'> & {
  startDate?: string | null;
  endDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

type WorkExperienceFormRawValue = FormValueOf<IWorkExperience>;

type NewWorkExperienceFormRawValue = FormValueOf<NewWorkExperience>;

type WorkExperienceFormDefaults = Pick<NewWorkExperience, 'id' | 'startDate' | 'endDate' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type WorkExperienceFormGroupContent = {
  id: FormControl<WorkExperienceFormRawValue['id'] | NewWorkExperience['id']>;
  jobTitle: FormControl<WorkExperienceFormRawValue['jobTitle']>;
  jobDesc: FormControl<WorkExperienceFormRawValue['jobDesc']>;
  companyName: FormControl<WorkExperienceFormRawValue['companyName']>;
  companyType: FormControl<WorkExperienceFormRawValue['companyType']>;
  orgAddress: FormControl<WorkExperienceFormRawValue['orgAddress']>;
  yearOfExp: FormControl<WorkExperienceFormRawValue['yearOfExp']>;
  startDate: FormControl<WorkExperienceFormRawValue['startDate']>;
  endDate: FormControl<WorkExperienceFormRawValue['endDate']>;
  lastModified: FormControl<WorkExperienceFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<WorkExperienceFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<WorkExperienceFormRawValue['createdBy']>;
  createdOn: FormControl<WorkExperienceFormRawValue['createdOn']>;
  isDeleted: FormControl<WorkExperienceFormRawValue['isDeleted']>;
  freeField1: FormControl<WorkExperienceFormRawValue['freeField1']>;
  freeField2: FormControl<WorkExperienceFormRawValue['freeField2']>;
  freeField3: FormControl<WorkExperienceFormRawValue['freeField3']>;
  freefield4: FormControl<WorkExperienceFormRawValue['freefield4']>;
  freefield5: FormControl<WorkExperienceFormRawValue['freefield5']>;
  employee: FormControl<WorkExperienceFormRawValue['employee']>;
};

export type WorkExperienceFormGroup = FormGroup<WorkExperienceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class WorkExperienceFormService {
  createWorkExperienceFormGroup(workExperience: WorkExperienceFormGroupInput = { id: null }): WorkExperienceFormGroup {
    const workExperienceRawValue = this.convertWorkExperienceToWorkExperienceRawValue({
      ...this.getFormDefaults(),
      ...workExperience,
    });
    return new FormGroup<WorkExperienceFormGroupContent>({
      id: new FormControl(
        { value: workExperienceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      jobTitle: new FormControl(workExperienceRawValue.jobTitle),
      jobDesc: new FormControl(workExperienceRawValue.jobDesc),
      companyName: new FormControl(workExperienceRawValue.companyName),
      companyType: new FormControl(workExperienceRawValue.companyType),
      orgAddress: new FormControl(workExperienceRawValue.orgAddress),
      yearOfExp: new FormControl(workExperienceRawValue.yearOfExp),
      startDate: new FormControl(workExperienceRawValue.startDate),
      endDate: new FormControl(workExperienceRawValue.endDate),
      lastModified: new FormControl(workExperienceRawValue.lastModified),
      lastModifiedBy: new FormControl(workExperienceRawValue.lastModifiedBy),
      createdBy: new FormControl(workExperienceRawValue.createdBy),
      createdOn: new FormControl(workExperienceRawValue.createdOn),
      isDeleted: new FormControl(workExperienceRawValue.isDeleted),
      freeField1: new FormControl(workExperienceRawValue.freeField1),
      freeField2: new FormControl(workExperienceRawValue.freeField2),
      freeField3: new FormControl(workExperienceRawValue.freeField3),
      freefield4: new FormControl(workExperienceRawValue.freefield4),
      freefield5: new FormControl(workExperienceRawValue.freefield5),
      employee: new FormControl(workExperienceRawValue.employee),
    });
  }

  getWorkExperience(form: WorkExperienceFormGroup): IWorkExperience | NewWorkExperience {
    return this.convertWorkExperienceRawValueToWorkExperience(
      form.getRawValue() as WorkExperienceFormRawValue | NewWorkExperienceFormRawValue
    );
  }

  resetForm(form: WorkExperienceFormGroup, workExperience: WorkExperienceFormGroupInput): void {
    const workExperienceRawValue = this.convertWorkExperienceToWorkExperienceRawValue({ ...this.getFormDefaults(), ...workExperience });
    form.reset(
      {
        ...workExperienceRawValue,
        id: { value: workExperienceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): WorkExperienceFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      startDate: currentTime,
      endDate: currentTime,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertWorkExperienceRawValueToWorkExperience(
    rawWorkExperience: WorkExperienceFormRawValue | NewWorkExperienceFormRawValue
  ): IWorkExperience | NewWorkExperience {
    return {
      ...rawWorkExperience,
      startDate: dayjs(rawWorkExperience.startDate, DATE_TIME_FORMAT),
      endDate: dayjs(rawWorkExperience.endDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawWorkExperience.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawWorkExperience.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertWorkExperienceToWorkExperienceRawValue(
    workExperience: IWorkExperience | (Partial<NewWorkExperience> & WorkExperienceFormDefaults)
  ): WorkExperienceFormRawValue | PartialWithRequiredKeyOf<NewWorkExperienceFormRawValue> {
    return {
      ...workExperience,
      startDate: workExperience.startDate ? workExperience.startDate.format(DATE_TIME_FORMAT) : undefined,
      endDate: workExperience.endDate ? workExperience.endDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: workExperience.lastModified ? workExperience.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: workExperience.createdOn ? workExperience.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
