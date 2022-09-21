import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEmployeeSeperation } from '../employee-seperation.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../employee-seperation.test-samples';

import { EmployeeSeperationService, RestEmployeeSeperation } from './employee-seperation.service';

const requireRestSample: RestEmployeeSeperation = {
  ...sampleWithRequiredData,
  seperationDate: sampleWithRequiredData.seperationDate?.toJSON(),
  updatedOn: sampleWithRequiredData.updatedOn?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
};

describe('EmployeeSeperation Service', () => {
  let service: EmployeeSeperationService;
  let httpMock: HttpTestingController;
  let expectedResult: IEmployeeSeperation | IEmployeeSeperation[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EmployeeSeperationService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a EmployeeSeperation', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const employeeSeperation = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(employeeSeperation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a EmployeeSeperation', () => {
      const employeeSeperation = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(employeeSeperation).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a EmployeeSeperation', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of EmployeeSeperation', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a EmployeeSeperation', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEmployeeSeperationToCollectionIfMissing', () => {
      it('should add a EmployeeSeperation to an empty array', () => {
        const employeeSeperation: IEmployeeSeperation = sampleWithRequiredData;
        expectedResult = service.addEmployeeSeperationToCollectionIfMissing([], employeeSeperation);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(employeeSeperation);
      });

      it('should not add a EmployeeSeperation to an array that contains it', () => {
        const employeeSeperation: IEmployeeSeperation = sampleWithRequiredData;
        const employeeSeperationCollection: IEmployeeSeperation[] = [
          {
            ...employeeSeperation,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEmployeeSeperationToCollectionIfMissing(employeeSeperationCollection, employeeSeperation);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a EmployeeSeperation to an array that doesn't contain it", () => {
        const employeeSeperation: IEmployeeSeperation = sampleWithRequiredData;
        const employeeSeperationCollection: IEmployeeSeperation[] = [sampleWithPartialData];
        expectedResult = service.addEmployeeSeperationToCollectionIfMissing(employeeSeperationCollection, employeeSeperation);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(employeeSeperation);
      });

      it('should add only unique EmployeeSeperation to an array', () => {
        const employeeSeperationArray: IEmployeeSeperation[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const employeeSeperationCollection: IEmployeeSeperation[] = [sampleWithRequiredData];
        expectedResult = service.addEmployeeSeperationToCollectionIfMissing(employeeSeperationCollection, ...employeeSeperationArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const employeeSeperation: IEmployeeSeperation = sampleWithRequiredData;
        const employeeSeperation2: IEmployeeSeperation = sampleWithPartialData;
        expectedResult = service.addEmployeeSeperationToCollectionIfMissing([], employeeSeperation, employeeSeperation2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(employeeSeperation);
        expect(expectedResult).toContain(employeeSeperation2);
      });

      it('should accept null and undefined values', () => {
        const employeeSeperation: IEmployeeSeperation = sampleWithRequiredData;
        expectedResult = service.addEmployeeSeperationToCollectionIfMissing([], null, employeeSeperation, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(employeeSeperation);
      });

      it('should return initial array if no EmployeeSeperation is added', () => {
        const employeeSeperationCollection: IEmployeeSeperation[] = [sampleWithRequiredData];
        expectedResult = service.addEmployeeSeperationToCollectionIfMissing(employeeSeperationCollection, undefined, null);
        expect(expectedResult).toEqual(employeeSeperationCollection);
      });
    });

    describe('compareEmployeeSeperation', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEmployeeSeperation(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEmployeeSeperation(entity1, entity2);
        const compareResult2 = service.compareEmployeeSeperation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEmployeeSeperation(entity1, entity2);
        const compareResult2 = service.compareEmployeeSeperation(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEmployeeSeperation(entity1, entity2);
        const compareResult2 = service.compareEmployeeSeperation(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
