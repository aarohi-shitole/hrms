import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EmployeeSeperationFormService } from './employee-seperation-form.service';
import { EmployeeSeperationService } from '../service/employee-seperation.service';
import { IEmployeeSeperation } from '../employee-seperation.model';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';

import { EmployeeSeperationUpdateComponent } from './employee-seperation-update.component';

describe('EmployeeSeperation Management Update Component', () => {
  let comp: EmployeeSeperationUpdateComponent;
  let fixture: ComponentFixture<EmployeeSeperationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let employeeSeperationFormService: EmployeeSeperationFormService;
  let employeeSeperationService: EmployeeSeperationService;
  let employeeService: EmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EmployeeSeperationUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(EmployeeSeperationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmployeeSeperationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    employeeSeperationFormService = TestBed.inject(EmployeeSeperationFormService);
    employeeSeperationService = TestBed.inject(EmployeeSeperationService);
    employeeService = TestBed.inject(EmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employee query and add missing value', () => {
      const employeeSeperation: IEmployeeSeperation = { id: 456 };
      const employee: IEmployee = { id: 90783 };
      employeeSeperation.employee = employee;

      const employeeCollection: IEmployee[] = [{ id: 51073 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [employee];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employeeSeperation });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        employeeCollection,
        ...additionalEmployees.map(expect.objectContaining)
      );
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const employeeSeperation: IEmployeeSeperation = { id: 456 };
      const employee: IEmployee = { id: 96444 };
      employeeSeperation.employee = employee;

      activatedRoute.data = of({ employeeSeperation });
      comp.ngOnInit();

      expect(comp.employeesSharedCollection).toContain(employee);
      expect(comp.employeeSeperation).toEqual(employeeSeperation);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployeeSeperation>>();
      const employeeSeperation = { id: 123 };
      jest.spyOn(employeeSeperationFormService, 'getEmployeeSeperation').mockReturnValue(employeeSeperation);
      jest.spyOn(employeeSeperationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employeeSeperation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employeeSeperation }));
      saveSubject.complete();

      // THEN
      expect(employeeSeperationFormService.getEmployeeSeperation).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(employeeSeperationService.update).toHaveBeenCalledWith(expect.objectContaining(employeeSeperation));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployeeSeperation>>();
      const employeeSeperation = { id: 123 };
      jest.spyOn(employeeSeperationFormService, 'getEmployeeSeperation').mockReturnValue({ id: null });
      jest.spyOn(employeeSeperationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employeeSeperation: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employeeSeperation }));
      saveSubject.complete();

      // THEN
      expect(employeeSeperationFormService.getEmployeeSeperation).toHaveBeenCalled();
      expect(employeeSeperationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployeeSeperation>>();
      const employeeSeperation = { id: 123 };
      jest.spyOn(employeeSeperationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employeeSeperation });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(employeeSeperationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEmployee', () => {
      it('Should forward to employeeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(employeeService, 'compareEmployee');
        comp.compareEmployee(entity, entity2);
        expect(employeeService.compareEmployee).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
