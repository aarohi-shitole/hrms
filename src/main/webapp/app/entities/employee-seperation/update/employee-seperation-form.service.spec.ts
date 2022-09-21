import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../employee-seperation.test-samples';

import { EmployeeSeperationFormService } from './employee-seperation-form.service';

describe('EmployeeSeperation Form Service', () => {
  let service: EmployeeSeperationFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmployeeSeperationFormService);
  });

  describe('Service methods', () => {
    describe('createEmployeeSeperationFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEmployeeSeperationFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            reasonForSeperation: expect.any(Object),
            seperationDate: expect.any(Object),
            comment: expect.any(Object),
            seperationStatus: expect.any(Object),
            otherReason: expect.any(Object),
            nagotiatedPeriod: expect.any(Object),
            createdBy: expect.any(Object),
            updatedOn: expect.any(Object),
            createdOn: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freefield1: expect.any(Object),
            freefield2: expect.any(Object),
            freefield3: expect.any(Object),
            freefield4: expect.any(Object),
            freefield5: expect.any(Object),
            employee: expect.any(Object),
          })
        );
      });

      it('passing IEmployeeSeperation should create a new form with FormGroup', () => {
        const formGroup = service.createEmployeeSeperationFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            reasonForSeperation: expect.any(Object),
            seperationDate: expect.any(Object),
            comment: expect.any(Object),
            seperationStatus: expect.any(Object),
            otherReason: expect.any(Object),
            nagotiatedPeriod: expect.any(Object),
            createdBy: expect.any(Object),
            updatedOn: expect.any(Object),
            createdOn: expect.any(Object),
            lastModified: expect.any(Object),
            lastModifiedBy: expect.any(Object),
            freefield1: expect.any(Object),
            freefield2: expect.any(Object),
            freefield3: expect.any(Object),
            freefield4: expect.any(Object),
            freefield5: expect.any(Object),
            employee: expect.any(Object),
          })
        );
      });
    });

    describe('getEmployeeSeperation', () => {
      it('should return NewEmployeeSeperation for default EmployeeSeperation initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEmployeeSeperationFormGroup(sampleWithNewData);

        const employeeSeperation = service.getEmployeeSeperation(formGroup) as any;

        expect(employeeSeperation).toMatchObject(sampleWithNewData);
      });

      it('should return NewEmployeeSeperation for empty EmployeeSeperation initial value', () => {
        const formGroup = service.createEmployeeSeperationFormGroup();

        const employeeSeperation = service.getEmployeeSeperation(formGroup) as any;

        expect(employeeSeperation).toMatchObject({});
      });

      it('should return IEmployeeSeperation', () => {
        const formGroup = service.createEmployeeSeperationFormGroup(sampleWithRequiredData);

        const employeeSeperation = service.getEmployeeSeperation(formGroup) as any;

        expect(employeeSeperation).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEmployeeSeperation should not enable id FormControl', () => {
        const formGroup = service.createEmployeeSeperationFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEmployeeSeperation should disable id FormControl', () => {
        const formGroup = service.createEmployeeSeperationFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
