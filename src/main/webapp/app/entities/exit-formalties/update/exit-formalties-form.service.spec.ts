import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../exit-formalties.test-samples';

import { ExitFormaltiesFormService } from './exit-formalties-form.service';

describe('ExitFormalties Form Service', () => {
  let service: ExitFormaltiesFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExitFormaltiesFormService);
  });

  describe('Service methods', () => {
    describe('createExitFormaltiesFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createExitFormaltiesFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            security: expect.any(Object),
            feedback: expect.any(Object),
            exitDate: expect.any(Object),
            exitInterview: expect.any(Object),
            libNoDue: expect.any(Object),
            noticePeriodServed: expect.any(Object),
            clearence: expect.any(Object),
            orgAssets: expect.any(Object),
            orgVehical: expect.any(Object),
            resignLetter: expect.any(Object),
            shares: expect.any(Object),
            staffWelfare: expect.any(Object),
            workForOrg: expect.any(Object),
            status: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freefield4: expect.any(Object),
            freefield5: expect.any(Object),
            employee: expect.any(Object),
          })
        );
      });

      it('passing IExitFormalties should create a new form with FormGroup', () => {
        const formGroup = service.createExitFormaltiesFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            security: expect.any(Object),
            feedback: expect.any(Object),
            exitDate: expect.any(Object),
            exitInterview: expect.any(Object),
            libNoDue: expect.any(Object),
            noticePeriodServed: expect.any(Object),
            clearence: expect.any(Object),
            orgAssets: expect.any(Object),
            orgVehical: expect.any(Object),
            resignLetter: expect.any(Object),
            shares: expect.any(Object),
            staffWelfare: expect.any(Object),
            workForOrg: expect.any(Object),
            status: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            isDeleted: expect.any(Object),
            freeField1: expect.any(Object),
            freeField2: expect.any(Object),
            freeField3: expect.any(Object),
            freefield4: expect.any(Object),
            freefield5: expect.any(Object),
            employee: expect.any(Object),
          })
        );
      });
    });

    describe('getExitFormalties', () => {
      it('should return NewExitFormalties for default ExitFormalties initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createExitFormaltiesFormGroup(sampleWithNewData);

        const exitFormalties = service.getExitFormalties(formGroup) as any;

        expect(exitFormalties).toMatchObject(sampleWithNewData);
      });

      it('should return NewExitFormalties for empty ExitFormalties initial value', () => {
        const formGroup = service.createExitFormaltiesFormGroup();

        const exitFormalties = service.getExitFormalties(formGroup) as any;

        expect(exitFormalties).toMatchObject({});
      });

      it('should return IExitFormalties', () => {
        const formGroup = service.createExitFormaltiesFormGroup(sampleWithRequiredData);

        const exitFormalties = service.getExitFormalties(formGroup) as any;

        expect(exitFormalties).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IExitFormalties should not enable id FormControl', () => {
        const formGroup = service.createExitFormaltiesFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewExitFormalties should disable id FormControl', () => {
        const formGroup = service.createExitFormaltiesFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
