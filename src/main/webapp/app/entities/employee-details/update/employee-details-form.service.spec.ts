import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../employee-details.test-samples';

import { EmployeeDetailsFormService } from './employee-details-form.service';

describe('EmployeeDetails Form Service', () => {
  let service: EmployeeDetailsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EmployeeDetailsFormService);
  });

  describe('Service methods', () => {
    describe('createEmployeeDetailsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createEmployeeDetailsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            age: expect.any(Object),
            fatherName: expect.any(Object),
            motherName: expect.any(Object),
            employeeId: expect.any(Object),
            yearsOfExperience: expect.any(Object),
            notes: expect.any(Object),
            bloodGroup: expect.any(Object),
            birthDate: expect.any(Object),
            designation: expect.any(Object),
            expertise: expect.any(Object),
            jobDescription: expect.any(Object),
            maritalStatus: expect.any(Object),
            secondaryContact: expect.any(Object),
            hobbies: expect.any(Object),
            areaInterest: expect.any(Object),
            noOfDependent: expect.any(Object),
            languageKnown: expect.any(Object),
            natinality: expect.any(Object),
            description: expect.any(Object),
            department: expect.any(Object),
            joiningDate: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            freefield1: expect.any(Object),
            freefield2: expect.any(Object),
            freefield3: expect.any(Object),
            freefield4: expect.any(Object),
            freefield5: expect.any(Object),
          })
        );
      });

      it('passing IEmployeeDetails should create a new form with FormGroup', () => {
        const formGroup = service.createEmployeeDetailsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            age: expect.any(Object),
            fatherName: expect.any(Object),
            motherName: expect.any(Object),
            employeeId: expect.any(Object),
            yearsOfExperience: expect.any(Object),
            notes: expect.any(Object),
            bloodGroup: expect.any(Object),
            birthDate: expect.any(Object),
            designation: expect.any(Object),
            expertise: expect.any(Object),
            jobDescription: expect.any(Object),
            maritalStatus: expect.any(Object),
            secondaryContact: expect.any(Object),
            hobbies: expect.any(Object),
            areaInterest: expect.any(Object),
            noOfDependent: expect.any(Object),
            languageKnown: expect.any(Object),
            natinality: expect.any(Object),
            description: expect.any(Object),
            department: expect.any(Object),
            joiningDate: expect.any(Object),
            createdBy: expect.any(Object),
            createdOn: expect.any(Object),
            freefield1: expect.any(Object),
            freefield2: expect.any(Object),
            freefield3: expect.any(Object),
            freefield4: expect.any(Object),
            freefield5: expect.any(Object),
          })
        );
      });
    });

    describe('getEmployeeDetails', () => {
      it('should return NewEmployeeDetails for default EmployeeDetails initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createEmployeeDetailsFormGroup(sampleWithNewData);

        const employeeDetails = service.getEmployeeDetails(formGroup) as any;

        expect(employeeDetails).toMatchObject(sampleWithNewData);
      });

      it('should return NewEmployeeDetails for empty EmployeeDetails initial value', () => {
        const formGroup = service.createEmployeeDetailsFormGroup();

        const employeeDetails = service.getEmployeeDetails(formGroup) as any;

        expect(employeeDetails).toMatchObject({});
      });

      it('should return IEmployeeDetails', () => {
        const formGroup = service.createEmployeeDetailsFormGroup(sampleWithRequiredData);

        const employeeDetails = service.getEmployeeDetails(formGroup) as any;

        expect(employeeDetails).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IEmployeeDetails should not enable id FormControl', () => {
        const formGroup = service.createEmployeeDetailsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewEmployeeDetails should disable id FormControl', () => {
        const formGroup = service.createEmployeeDetailsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
