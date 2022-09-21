import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEducation, NewEducation } from '../education.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEducation for edit and NewEducationFormGroupInput for create.
 */
type EducationFormGroupInput = IEducation | PartialWithRequiredKeyOf<NewEducation>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEducation | NewEducation> = Omit<T, 'startDate' | 'endDate' | 'lastModified' | 'createdOn'> & {
  startDate?: string | null;
  endDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

type EducationFormRawValue = FormValueOf<IEducation>;

type NewEducationFormRawValue = FormValueOf<NewEducation>;

type EducationFormDefaults = Pick<NewEducation, 'id' | 'startDate' | 'endDate' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type EducationFormGroupContent = {
  id: FormControl<EducationFormRawValue['id'] | NewEducation['id']>;
  schoolName: FormControl<EducationFormRawValue['schoolName']>;
  degree: FormControl<EducationFormRawValue['degree']>;
  sector: FormControl<EducationFormRawValue['sector']>;
  eduType: FormControl<EducationFormRawValue['eduType']>;
  startDate: FormControl<EducationFormRawValue['startDate']>;
  endDate: FormControl<EducationFormRawValue['endDate']>;
  grade: FormControl<EducationFormRawValue['grade']>;
  description: FormControl<EducationFormRawValue['description']>;
  lastModified: FormControl<EducationFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<EducationFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<EducationFormRawValue['createdBy']>;
  createdOn: FormControl<EducationFormRawValue['createdOn']>;
  isDeleted: FormControl<EducationFormRawValue['isDeleted']>;
  freeField1: FormControl<EducationFormRawValue['freeField1']>;
  freeField2: FormControl<EducationFormRawValue['freeField2']>;
  freeField3: FormControl<EducationFormRawValue['freeField3']>;
  employee: FormControl<EducationFormRawValue['employee']>;
};

export type EducationFormGroup = FormGroup<EducationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EducationFormService {
  createEducationFormGroup(education: EducationFormGroupInput = { id: null }): EducationFormGroup {
    const educationRawValue = this.convertEducationToEducationRawValue({
      ...this.getFormDefaults(),
      ...education,
    });
    return new FormGroup<EducationFormGroupContent>({
      id: new FormControl(
        { value: educationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      schoolName: new FormControl(educationRawValue.schoolName),
      degree: new FormControl(educationRawValue.degree),
      sector: new FormControl(educationRawValue.sector),
      eduType: new FormControl(educationRawValue.eduType),
      startDate: new FormControl(educationRawValue.startDate),
      endDate: new FormControl(educationRawValue.endDate),
      grade: new FormControl(educationRawValue.grade),
      description: new FormControl(educationRawValue.description),
      lastModified: new FormControl(educationRawValue.lastModified),
      lastModifiedBy: new FormControl(educationRawValue.lastModifiedBy),
      createdBy: new FormControl(educationRawValue.createdBy),
      createdOn: new FormControl(educationRawValue.createdOn),
      isDeleted: new FormControl(educationRawValue.isDeleted),
      freeField1: new FormControl(educationRawValue.freeField1),
      freeField2: new FormControl(educationRawValue.freeField2),
      freeField3: new FormControl(educationRawValue.freeField3),
      employee: new FormControl(educationRawValue.employee),
    });
  }

  getEducation(form: EducationFormGroup): IEducation | NewEducation {
    return this.convertEducationRawValueToEducation(form.getRawValue() as EducationFormRawValue | NewEducationFormRawValue);
  }

  resetForm(form: EducationFormGroup, education: EducationFormGroupInput): void {
    const educationRawValue = this.convertEducationToEducationRawValue({ ...this.getFormDefaults(), ...education });
    form.reset(
      {
        ...educationRawValue,
        id: { value: educationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): EducationFormDefaults {
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

  private convertEducationRawValueToEducation(rawEducation: EducationFormRawValue | NewEducationFormRawValue): IEducation | NewEducation {
    return {
      ...rawEducation,
      startDate: dayjs(rawEducation.startDate, DATE_TIME_FORMAT),
      endDate: dayjs(rawEducation.endDate, DATE_TIME_FORMAT),
      lastModified: dayjs(rawEducation.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawEducation.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertEducationToEducationRawValue(
    education: IEducation | (Partial<NewEducation> & EducationFormDefaults)
  ): EducationFormRawValue | PartialWithRequiredKeyOf<NewEducationFormRawValue> {
    return {
      ...education,
      startDate: education.startDate ? education.startDate.format(DATE_TIME_FORMAT) : undefined,
      endDate: education.endDate ? education.endDate.format(DATE_TIME_FORMAT) : undefined,
      lastModified: education.lastModified ? education.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: education.createdOn ? education.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
