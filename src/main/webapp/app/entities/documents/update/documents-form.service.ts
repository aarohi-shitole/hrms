import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IDocuments, NewDocuments } from '../documents.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IDocuments for edit and NewDocumentsFormGroupInput for create.
 */
type DocumentsFormGroupInput = IDocuments | PartialWithRequiredKeyOf<NewDocuments>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IDocuments | NewDocuments> = Omit<T, 'issuedDate' | 'validUpTo' | 'lastModified' | 'createdOn'> & {
  issuedDate?: string | null;
  validUpTo?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

type DocumentsFormRawValue = FormValueOf<IDocuments>;

type NewDocumentsFormRawValue = FormValueOf<NewDocuments>;

type DocumentsFormDefaults = Pick<
  NewDocuments,
  'id' | 'issuedDate' | 'validUpTo' | 'hasVerified' | 'lastModified' | 'createdOn' | 'isDeleted'
>;

type DocumentsFormGroupContent = {
  id: FormControl<DocumentsFormRawValue['id'] | NewDocuments['id']>;
  referenceId: FormControl<DocumentsFormRawValue['referenceId']>;
  type: FormControl<DocumentsFormRawValue['type']>;
  subtype: FormControl<DocumentsFormRawValue['subtype']>;
  fileName: FormControl<DocumentsFormRawValue['fileName']>;
  filePath: FormControl<DocumentsFormRawValue['filePath']>;
  fileUrl: FormControl<DocumentsFormRawValue['fileUrl']>;
  issuedDate: FormControl<DocumentsFormRawValue['issuedDate']>;
  validUpTo: FormControl<DocumentsFormRawValue['validUpTo']>;
  placeOfIssued: FormControl<DocumentsFormRawValue['placeOfIssued']>;
  hasVerified: FormControl<DocumentsFormRawValue['hasVerified']>;
  remark: FormControl<DocumentsFormRawValue['remark']>;
  lastModified: FormControl<DocumentsFormRawValue['lastModified']>;
  lastModifiedBy: FormControl<DocumentsFormRawValue['lastModifiedBy']>;
  createdBy: FormControl<DocumentsFormRawValue['createdBy']>;
  createdOn: FormControl<DocumentsFormRawValue['createdOn']>;
  isDeleted: FormControl<DocumentsFormRawValue['isDeleted']>;
  freeField1: FormControl<DocumentsFormRawValue['freeField1']>;
  freeField2: FormControl<DocumentsFormRawValue['freeField2']>;
  freeField3: FormControl<DocumentsFormRawValue['freeField3']>;
  employee: FormControl<DocumentsFormRawValue['employee']>;
  organization: FormControl<DocumentsFormRawValue['organization']>;
};

export type DocumentsFormGroup = FormGroup<DocumentsFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class DocumentsFormService {
  createDocumentsFormGroup(documents: DocumentsFormGroupInput = { id: null }): DocumentsFormGroup {
    const documentsRawValue = this.convertDocumentsToDocumentsRawValue({
      ...this.getFormDefaults(),
      ...documents,
    });
    return new FormGroup<DocumentsFormGroupContent>({
      id: new FormControl(
        { value: documentsRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      referenceId: new FormControl(documentsRawValue.referenceId),
      type: new FormControl(documentsRawValue.type),
      subtype: new FormControl(documentsRawValue.subtype),
      fileName: new FormControl(documentsRawValue.fileName),
      filePath: new FormControl(documentsRawValue.filePath),
      fileUrl: new FormControl(documentsRawValue.fileUrl),
      issuedDate: new FormControl(documentsRawValue.issuedDate),
      validUpTo: new FormControl(documentsRawValue.validUpTo),
      placeOfIssued: new FormControl(documentsRawValue.placeOfIssued),
      hasVerified: new FormControl(documentsRawValue.hasVerified),
      remark: new FormControl(documentsRawValue.remark),
      lastModified: new FormControl(documentsRawValue.lastModified),
      lastModifiedBy: new FormControl(documentsRawValue.lastModifiedBy),
      createdBy: new FormControl(documentsRawValue.createdBy),
      createdOn: new FormControl(documentsRawValue.createdOn),
      isDeleted: new FormControl(documentsRawValue.isDeleted),
      freeField1: new FormControl(documentsRawValue.freeField1),
      freeField2: new FormControl(documentsRawValue.freeField2),
      freeField3: new FormControl(documentsRawValue.freeField3),
      employee: new FormControl(documentsRawValue.employee),
      organization: new FormControl(documentsRawValue.organization),
    });
  }

  getDocuments(form: DocumentsFormGroup): IDocuments | NewDocuments {
    return this.convertDocumentsRawValueToDocuments(form.getRawValue() as DocumentsFormRawValue | NewDocumentsFormRawValue);
  }

  resetForm(form: DocumentsFormGroup, documents: DocumentsFormGroupInput): void {
    const documentsRawValue = this.convertDocumentsToDocumentsRawValue({ ...this.getFormDefaults(), ...documents });
    form.reset(
      {
        ...documentsRawValue,
        id: { value: documentsRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): DocumentsFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      issuedDate: currentTime,
      validUpTo: currentTime,
      hasVerified: false,
      lastModified: currentTime,
      createdOn: currentTime,
      isDeleted: false,
    };
  }

  private convertDocumentsRawValueToDocuments(rawDocuments: DocumentsFormRawValue | NewDocumentsFormRawValue): IDocuments | NewDocuments {
    return {
      ...rawDocuments,
      issuedDate: dayjs(rawDocuments.issuedDate, DATE_TIME_FORMAT),
      validUpTo: dayjs(rawDocuments.validUpTo, DATE_TIME_FORMAT),
      lastModified: dayjs(rawDocuments.lastModified, DATE_TIME_FORMAT),
      createdOn: dayjs(rawDocuments.createdOn, DATE_TIME_FORMAT),
    };
  }

  private convertDocumentsToDocumentsRawValue(
    documents: IDocuments | (Partial<NewDocuments> & DocumentsFormDefaults)
  ): DocumentsFormRawValue | PartialWithRequiredKeyOf<NewDocumentsFormRawValue> {
    return {
      ...documents,
      issuedDate: documents.issuedDate ? documents.issuedDate.format(DATE_TIME_FORMAT) : undefined,
      validUpTo: documents.validUpTo ? documents.validUpTo.format(DATE_TIME_FORMAT) : undefined,
      lastModified: documents.lastModified ? documents.lastModified.format(DATE_TIME_FORMAT) : undefined,
      createdOn: documents.createdOn ? documents.createdOn.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
