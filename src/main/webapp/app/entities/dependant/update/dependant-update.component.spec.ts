import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DependantFormService } from './dependant-form.service';
import { DependantService } from '../service/dependant.service';
import { IDependant } from '../dependant.model';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';

import { DependantUpdateComponent } from './dependant-update.component';

describe('Dependant Management Update Component', () => {
  let comp: DependantUpdateComponent;
  let fixture: ComponentFixture<DependantUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let dependantFormService: DependantFormService;
  let dependantService: DependantService;
  let employeeService: EmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DependantUpdateComponent],
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
      .overrideTemplate(DependantUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DependantUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    dependantFormService = TestBed.inject(DependantFormService);
    dependantService = TestBed.inject(DependantService);
    employeeService = TestBed.inject(EmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employee query and add missing value', () => {
      const dependant: IDependant = { id: 456 };
      const employee: IEmployee = { id: 42711 };
      dependant.employee = employee;

      const employeeCollection: IEmployee[] = [{ id: 99327 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [employee];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ dependant });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        employeeCollection,
        ...additionalEmployees.map(expect.objectContaining)
      );
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const dependant: IDependant = { id: 456 };
      const employee: IEmployee = { id: 75768 };
      dependant.employee = employee;

      activatedRoute.data = of({ dependant });
      comp.ngOnInit();

      expect(comp.employeesSharedCollection).toContain(employee);
      expect(comp.dependant).toEqual(dependant);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDependant>>();
      const dependant = { id: 123 };
      jest.spyOn(dependantFormService, 'getDependant').mockReturnValue(dependant);
      jest.spyOn(dependantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dependant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dependant }));
      saveSubject.complete();

      // THEN
      expect(dependantFormService.getDependant).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(dependantService.update).toHaveBeenCalledWith(expect.objectContaining(dependant));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDependant>>();
      const dependant = { id: 123 };
      jest.spyOn(dependantFormService, 'getDependant').mockReturnValue({ id: null });
      jest.spyOn(dependantService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dependant: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: dependant }));
      saveSubject.complete();

      // THEN
      expect(dependantFormService.getDependant).toHaveBeenCalled();
      expect(dependantService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IDependant>>();
      const dependant = { id: 123 };
      jest.spyOn(dependantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ dependant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(dependantService.update).toHaveBeenCalled();
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
