import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ExitFormaltiesFormService } from './exit-formalties-form.service';
import { ExitFormaltiesService } from '../service/exit-formalties.service';
import { IExitFormalties } from '../exit-formalties.model';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';

import { ExitFormaltiesUpdateComponent } from './exit-formalties-update.component';

describe('ExitFormalties Management Update Component', () => {
  let comp: ExitFormaltiesUpdateComponent;
  let fixture: ComponentFixture<ExitFormaltiesUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let exitFormaltiesFormService: ExitFormaltiesFormService;
  let exitFormaltiesService: ExitFormaltiesService;
  let employeeService: EmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ExitFormaltiesUpdateComponent],
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
      .overrideTemplate(ExitFormaltiesUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ExitFormaltiesUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    exitFormaltiesFormService = TestBed.inject(ExitFormaltiesFormService);
    exitFormaltiesService = TestBed.inject(ExitFormaltiesService);
    employeeService = TestBed.inject(EmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employee query and add missing value', () => {
      const exitFormalties: IExitFormalties = { id: 456 };
      const employee: IEmployee = { id: 66367 };
      exitFormalties.employee = employee;

      const employeeCollection: IEmployee[] = [{ id: 27769 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [employee];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ exitFormalties });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        employeeCollection,
        ...additionalEmployees.map(expect.objectContaining)
      );
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const exitFormalties: IExitFormalties = { id: 456 };
      const employee: IEmployee = { id: 32726 };
      exitFormalties.employee = employee;

      activatedRoute.data = of({ exitFormalties });
      comp.ngOnInit();

      expect(comp.employeesSharedCollection).toContain(employee);
      expect(comp.exitFormalties).toEqual(exitFormalties);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExitFormalties>>();
      const exitFormalties = { id: 123 };
      jest.spyOn(exitFormaltiesFormService, 'getExitFormalties').mockReturnValue(exitFormalties);
      jest.spyOn(exitFormaltiesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ exitFormalties });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: exitFormalties }));
      saveSubject.complete();

      // THEN
      expect(exitFormaltiesFormService.getExitFormalties).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(exitFormaltiesService.update).toHaveBeenCalledWith(expect.objectContaining(exitFormalties));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExitFormalties>>();
      const exitFormalties = { id: 123 };
      jest.spyOn(exitFormaltiesFormService, 'getExitFormalties').mockReturnValue({ id: null });
      jest.spyOn(exitFormaltiesService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ exitFormalties: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: exitFormalties }));
      saveSubject.complete();

      // THEN
      expect(exitFormaltiesFormService.getExitFormalties).toHaveBeenCalled();
      expect(exitFormaltiesService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IExitFormalties>>();
      const exitFormalties = { id: 123 };
      jest.spyOn(exitFormaltiesService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ exitFormalties });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(exitFormaltiesService.update).toHaveBeenCalled();
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
