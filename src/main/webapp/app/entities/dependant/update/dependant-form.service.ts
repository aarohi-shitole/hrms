import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDependant, NewDependant } from '../dependant.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDependant for edit and NewDependantFormGroupInput for create.
 */
type DependantFormGroupInput = IDependant | PartialWithRequiredKeyOf<NewDependant>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDependant | NewDependant> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type DependantFormRawValue = FormValueOf<IDependant>;

type NewDependantFormRawValue = FormValueOf<NewDependant>;

type DependantFormDefaults = Pick<NewDependant, 'id' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type DependantFormGroupContent = {
  id: FormControl<DependantFormRawValue['id'] | NewDependant['id']>;
  name: FormControl<DependantFormRawValue['name']>;
  age: FormControl<DependantFormRawValue['age']>;
  dateOfBirth: FormControl<DependantFormRawValue['dateOfBirth']>;
  type: FormControl<DependantFormRawValue['type']>;
  mobile: FormControl<DependantFormRawValue['mobile']>;
  address: FormControl<DependantFormRawValue['address']>;
  lastModified: FormControl<DependantFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<DependantFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<DependantFormRawValue['createdBy']>;
  createdOn: FormControl<DependantFormRawValue['createdOn']>;
  isDeleted: FormControl<DependantFormRawValue['isDeleted']>;
  freeField1: FormControl<DependantFormRawValue['freeField1']>;
  freeField2: FormControl<DependantFormRawValue['freeField2']>;
  freeField3: FormControl<DependantFormRawValue['freeField3']>;
  freefield4: FormControl<DependantFormRawValue['freefield4']>;
  freefield5: FormControl<DependantFormRawValue['freefield5']>;
  employee: FormControl<DependantFormRawValue['employee']>;
};

export type DependantFormGroup = FormGroup<DependantFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DependantFormService {
  createDependantFormGroup(dependant: DependantFormGroupInput = { id: null }): DependantFormGroup {
    const dependantRawValue = this.convertDependantToDependantRawValue({
      ...this.getFormDefaults(),
      ...dependant,
    });
    return new FormGroup<DependantFormGroupContent>({
      id: new FormControl(
        { value: dependantRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(dependantRawValue.name),
      age: new FormControl(dependantRawValue.age),
      dateOfBirth: new FormControl(dependantRawValue.dateOfBirth),
      type: new FormControl(dependantRawValue.type),
      mobile: new FormControl(dependantRawValue.mobile),
      address: new FormControl(dependantRawValue.address),
      lastModified: new FormControl(dependantRawValue.lastModified),
      lastModifiedBy: new FormControl(dependantRawValue.lastModifiedBy),
      createdBy: new FormControl(dependantRawValue.createdBy),
      createdOn: new FormControl(dependantRawValue.createdOn),
      isDeleted: new FormControl(dependantRawValue.isDeleted),
      freeField1: new FormControl(dependantRawValue.freeField1),
      freeField2: new FormControl(dependantRawValue.freeField2),
      freeField3: new FormControl(dependantRawValue.freeField3),
      freefield4: new FormControl(dependantRawValue.freefield4),
      freefield5: new FormControl(dependantRawValue.freefield5),
      employee: new FormControl(dependantRawValue.employee),
    });
  }

  getDependant(form: DependantFormGroup): IDependant | NewDependant {
    return this.convertDependantRawValueToDependant(form.getRawValue() as DependantFormRawValue | NewDependantFormRawValue);
  }

  resetForm(form: DependantFormGroup, dependant: DependantFormGroupInput): void {
    const dependantRawValue = this.convertDependantToDependantRawValue({ ...this.getFormDefaults(), ...dependant });
    form.reset(
      {
        ...dependantRawValue,
        id: { value: dependantRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DependantFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertDependantRawValueToDependant(rawDependant: DependantFormRawValue | NewDependantFormRawValue): IDependant | NewDependant {
    return {
      ...rawDependant,
      lastModified: dayjs(rawDependant.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawDependant.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertDependantToDependantRawValue(
    dependant: IDependant | (Partial<NewDependant> & DependantFormDefaults)
  ): DependantFormRawValue | PartialWithRequiredKeyOf<NewDependantFormRawValue> {
    return {
      ...dependant,
      lastModified: dependant.lastModified ? dependant.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: dependant.createdOn ? dependant.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
