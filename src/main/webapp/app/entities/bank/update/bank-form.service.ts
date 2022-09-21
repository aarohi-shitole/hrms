import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IBank, NewBank } from '../bank.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IBank for edit and NewBankFormGroupInput for create.
 */
type BankFormGroupInput = IBank | PartialWithRequiredKeyOf<NewBank>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IBank | NewBank> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

type BankFormRawValue = FormValueOf<IBank>;

type NewBankFormRawValue = FormValueOf<NewBank>;

type BankFormDefaults = Pick<NewBank, 'id' | 'lastModified' | 'createdOn' | 'isDeleted'>;

type BankFormGroupContent = {
  id: FormControl<BankFormRawValue['id'] | NewBank['id']>;
  accountNo: FormControl<BankFormRawValue['accountNo']>;
  name: FormControl<BankFormRawValue['name']>;
  branch: FormControl<BankFormRawValue['branch']>;
  ifscCode: FormControl<BankFormRawValue['ifscCode']>;
  mcirCode: FormControl<BankFormRawValue['mcirCode']>;
  lastModified: FormControl<BankFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<BankFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<BankFormRawValue['createdBy']>;
  createdOn: FormControl<BankFormRawValue['createdOn']>;
  isDeleted: FormControl<BankFormRawValue['isDeleted']>;
  freeField1: FormControl<BankFormRawValue['freeField1']>;
  freeField2: FormControl<BankFormRawValue['freeField2']>;
  freeField3: FormControl<BankFormRawValue['freeField3']>;
  employee: FormControl<BankFormRawValue['employee']>;
  organization: FormControl<BankFormRawValue['organization']>;
};

export type BankFormGroup = FormGroup<BankFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class BankFormService {
  createBankFormGroup(bank: BankFormGroupInput = { id: null }): BankFormGroup {
    const bankRawValue = this.convertBankToBankRawValue({
      ...this.getFormDefaults(),
      ...bank,
    });
    return new FormGroup<BankFormGroupContent>({
      id: new FormControl(
        { value: bankRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      accountNo: new FormControl(bankRawValue.accountNo),
      name: new FormControl(bankRawValue.name),
      branch: new FormControl(bankRawValue.branch),
      ifscCode: new FormControl(bankRawValue.ifscCode),
      mcirCode: new FormControl(bankRawValue.mcirCode),
      lastModified: new FormControl(bankRawValue.lastModified),
      lastModifiedBy: new FormControl(bankRawValue.lastModifiedBy),
      createdBy: new FormControl(bankRawValue.createdBy),
      createdOn: new FormControl(bankRawValue.createdOn),
      isDeleted: new FormControl(bankRawValue.isDeleted),
      freeField1: new FormControl(bankRawValue.freeField1),
      freeField2: new FormControl(bankRawValue.freeField2),
      freeField3: new FormControl(bankRawValue.freeField3),
      employee: new FormControl(bankRawValue.employee),
      organization: new FormControl(bankRawValue.organization),
    });
  }

  getBank(form: BankFormGroup): IBank | NewBank {
    return this.convertBankRawValueToBank(form.getRawValue() as BankFormRawValue | NewBankFormRawValue);
  }

  resetForm(form: BankFormGroup, bank: BankFormGroupInput): void {
    const bankRawValue = this.convertBankToBankRawValue({ ...this.getFormDefaults(), ...bank });
    form.reset(
      {
        ...bankRawValue,
        id: { value: bankRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): BankFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertBankRawValueToBank(rawBank: BankFormRawValue | NewBankFormRawValue): IBank | NewBank {
    return {
      ...rawBank,
      lastModified: dayjs(rawBank.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawBank.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertBankToBankRawValue(
    bank: IBank | (Partial<NewBank> & BankFormDefaults)
  ): BankFormRawValue | PartialWithRequiredKeyOf<NewBankFormRawValue> {
    return {
      ...bank,
      lastModified: bank.lastModified ? bank.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: bank.createdOn ? bank.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
