import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IEmployeeSeperation } from '../employee-seperation.model';
import { EmployeeSeperationService } from '../service/employee-seperation.service';

import { EmployeeSeperationRoutingResolveService } from './employee-seperation-routing-resolve.service';

describe('EmployeeSeperation routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: EmployeeSeperationRoutingResolveService;
  let service: EmployeeSeperationService;
  let resultEmployeeSeperation: IEmployeeSeperation | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(EmployeeSeperationRoutingResolveService);
    service = TestBed.inject(EmployeeSeperationService);
    resultEmployeeSeperation = undefined;
  });

  describe('resolve', () => {
    it('should return IEmployeeSeperation returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEmployeeSeperation = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEmployeeSeperation).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEmployeeSeperation = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultEmployeeSeperation).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IEmployeeSeperation>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultEmployeeSeperation = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultEmployeeSeperation).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
