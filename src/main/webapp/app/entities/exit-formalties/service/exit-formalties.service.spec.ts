import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IExitFormalties } from '../exit-formalties.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../exit-formalties.test-samples';

import { ExitFormaltiesService, RestExitFormalties } from './exit-formalties.service';

const requireRestSample: RestExitFormalties = {
  ...sampleWithRequiredData,
  exitDate: sampleWithRequiredData.exitDate?.toJSON(),
  lastModified: sampleWithRequiredData.lastModified?.toJSON(),
  createdOn: sampleWithRequiredData.createdOn?.toJSON(),
};

describe('ExitFormalties Service', () => {
  let service: ExitFormaltiesService;
  let httpMock: HttpTestingController;
  let expectedResult: IExitFormalties | IExitFormalties[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ExitFormaltiesService);
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

    it('should create a ExitFormalties', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const exitFormalties = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(exitFormalties).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ExitFormalties', () => {
      const exitFormalties = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(exitFormalties).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ExitFormalties', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ExitFormalties', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ExitFormalties', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addExitFormaltiesToCollectionIfMissing', () => {
      it('should add a ExitFormalties to an empty array', () => {
        const exitFormalties: IExitFormalties = sampleWithRequiredData;
        expectedResult = service.addExitFormaltiesToCollectionIfMissing([], exitFormalties);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(exitFormalties);
      });

      it('should not add a ExitFormalties to an array that contains it', () => {
        const exitFormalties: IExitFormalties = sampleWithRequiredData;
        const exitFormaltiesCollection: IExitFormalties[] = [
          {
            ...exitFormalties,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addExitFormaltiesToCollectionIfMissing(exitFormaltiesCollection, exitFormalties);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ExitFormalties to an array that doesn't contain it", () => {
        const exitFormalties: IExitFormalties = sampleWithRequiredData;
        const exitFormaltiesCollection: IExitFormalties[] = [sampleWithPartialData];
        expectedResult = service.addExitFormaltiesToCollectionIfMissing(exitFormaltiesCollection, exitFormalties);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(exitFormalties);
      });

      it('should add only unique ExitFormalties to an array', () => {
        const exitFormaltiesArray: IExitFormalties[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const exitFormaltiesCollection: IExitFormalties[] = [sampleWithRequiredData];
        expectedResult = service.addExitFormaltiesToCollectionIfMissing(exitFormaltiesCollection, ...exitFormaltiesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const exitFormalties: IExitFormalties = sampleWithRequiredData;
        const exitFormalties2: IExitFormalties = sampleWithPartialData;
        expectedResult = service.addExitFormaltiesToCollectionIfMissing([], exitFormalties, exitFormalties2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(exitFormalties);
        expect(expectedResult).toContain(exitFormalties2);
      });

      it('should accept null and undefined values', () => {
        const exitFormalties: IExitFormalties = sampleWithRequiredData;
        expectedResult = service.addExitFormaltiesToCollectionIfMissing([], null, exitFormalties, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(exitFormalties);
      });

      it('should return initial array if no ExitFormalties is added', () => {
        const exitFormaltiesCollection: IExitFormalties[] = [sampleWithRequiredData];
        expectedResult = service.addExitFormaltiesToCollectionIfMissing(exitFormaltiesCollection, undefined, null);
        expect(expectedResult).toEqual(exitFormaltiesCollection);
      });
    });

    describe('compareExitFormalties', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareExitFormalties(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareExitFormalties(entity1, entity2);
        const compareResult2 = service.compareExitFormalties(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareExitFormalties(entity1, entity2);
        const compareResult2 = service.compareExitFormalties(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareExitFormalties(entity1, entity2);
        const compareResult2 = service.compareExitFormalties(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
