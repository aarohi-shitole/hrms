import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../dependant.test-samples';

import { DependantFormService } from './dependant-form.service';

describe('Dependant Form Service', () => {
  let service: DependantFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DependantFormService);
  });

  describe('Service methods', () => {
    describe('createDependantFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createDependantFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            age: expect.any(Object),
            dateOfBirth: expect.any(Object),
            type: expect.any(Object),
            mobile: expect.any(Object),
            address: expect.any(Object),
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

      it('passing IDependant should create a new form with FormGroup', () => {
        const formGroup = service.createDependantFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
            age: expect.any(Object),
            dateOfBirth: expect.any(Object),
            type: expect.any(Object),
            mobile: expect.any(Object),
            address: expect.any(Object),
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

    describe('getDependant', () => {
      it('should return NewDependant for default Dependant initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createDependantFormGroup(sampleWithNewData);

        const dependant = service.getDependant(formGroup) as any;

        expect(dependant).toMatchObject(sampleWithNewData);
      });

      it('should return NewDependant for empty Dependant initial value', () => {
        const formGroup = service.createDependantFormGroup();

        const dependant = service.getDependant(formGroup) as any;

        expect(dependant).toMatchObject({});
      });

      it('should return IDependant', () => {
        const formGroup = service.createDependantFormGroup(sampleWithRequiredData);

        const dependant = service.getDependant(formGroup) as any;

        expect(dependant).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IDependant should not enable id FormControl', () => {
        const formGroup = service.createDependantFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewDependant should disable id FormControl', () => {
        const formGroup = service.createDependantFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
