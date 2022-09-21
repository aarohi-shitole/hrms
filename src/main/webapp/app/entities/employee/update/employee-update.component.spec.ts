import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EmployeeFormService } from './employee-form.service';
import { EmployeeService } from '../service/employee.service';
import { IEmployee } from '../employee.model';
import { IEmployeeDetails } from 'app/entities/employee-details/employee-details.model';
import { EmployeeDetailsService } from 'app/entities/employee-details/service/employee-details.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';
import { IIncomeTaxSlab } from 'app/entities/income-tax-slab/income-tax-slab.model';
import { IncomeTaxSlabService } from 'app/entities/income-tax-slab/service/income-tax-slab.service';
import { ISecurityPermission } from 'app/entities/security-permission/security-permission.model';
import { SecurityPermissionService } from 'app/entities/security-permission/service/security-permission.service';
import { ISecurityRole } from 'app/entities/security-role/security-role.model';
import { SecurityRoleService } from 'app/entities/security-role/service/security-role.service';

import { EmployeeUpdateComponent } from './employee-update.component';

describe('Employee Management Update Component', () => {
  let comp: EmployeeUpdateComponent;
  let fixture: ComponentFixture<EmployeeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let employeeFormService: EmployeeFormService;
  let employeeService: EmployeeService;
  let employeeDetailsService: EmployeeDetailsService;
  let organizationService: OrganizationService;
  let incomeTaxSlabService: IncomeTaxSlabService;
  let securityPermissionService: SecurityPermissionService;
  let securityRoleService: SecurityRoleService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EmployeeUpdateComponent],
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
      .overrideTemplate(EmployeeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmployeeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    employeeFormService = TestBed.inject(EmployeeFormService);
    employeeService = TestBed.inject(EmployeeService);
    employeeDetailsService = TestBed.inject(EmployeeDetailsService);
    organizationService = TestBed.inject(OrganizationService);
    incomeTaxSlabService = TestBed.inject(IncomeTaxSlabService);
    securityPermissionService = TestBed.inject(SecurityPermissionService);
    securityRoleService = TestBed.inject(SecurityRoleService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EmployeeDetails query and add missing value', () => {
      const employee: IEmployee = { id: 456 };
      const employeeDetails: IEmployeeDetails = { id: 86797 };
      employee.employeeDetails = employeeDetails;

      const employeeDetailsCollection: IEmployeeDetails[] = [{ id: 38775 }];
      jest.spyOn(employeeDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeDetailsCollection })));
      const additionalEmployeeDetails = [employeeDetails];
      const expectedCollection: IEmployeeDetails[] = [...additionalEmployeeDetails, ...employeeDetailsCollection];
      jest.spyOn(employeeDetailsService, 'addEmployeeDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(employeeDetailsService.query).toHaveBeenCalled();
      expect(employeeDetailsService.addEmployeeDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        employeeDetailsCollection,
        ...additionalEmployeeDetails.map(expect.objectContaining)
      );
      expect(comp.employeeDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Organization query and add missing value', () => {
      const employee: IEmployee = { id: 456 };
      const organization: IOrganization = { id: 54205 };
      employee.organization = organization;

      const organizationCollection: IOrganization[] = [{ id: 72047 }];
      jest.spyOn(organizationService, 'query').mockReturnValue(of(new HttpResponse({ body: organizationCollection })));
      const additionalOrganizations = [organization];
      const expectedCollection: IOrganization[] = [...additionalOrganizations, ...organizationCollection];
      jest.spyOn(organizationService, 'addOrganizationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(organizationService.query).toHaveBeenCalled();
      expect(organizationService.addOrganizationToCollectionIfMissing).toHaveBeenCalledWith(
        organizationCollection,
        ...additionalOrganizations.map(expect.objectContaining)
      );
      expect(comp.organizationsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call IncomeTaxSlab query and add missing value', () => {
      const employee: IEmployee = { id: 456 };
      const incomeTaxSlab: IIncomeTaxSlab = { id: 75442 };
      employee.incomeTaxSlab = incomeTaxSlab;

      const incomeTaxSlabCollection: IIncomeTaxSlab[] = [{ id: 51227 }];
      jest.spyOn(incomeTaxSlabService, 'query').mockReturnValue(of(new HttpResponse({ body: incomeTaxSlabCollection })));
      const additionalIncomeTaxSlabs = [incomeTaxSlab];
      const expectedCollection: IIncomeTaxSlab[] = [...additionalIncomeTaxSlabs, ...incomeTaxSlabCollection];
      jest.spyOn(incomeTaxSlabService, 'addIncomeTaxSlabToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(incomeTaxSlabService.query).toHaveBeenCalled();
      expect(incomeTaxSlabService.addIncomeTaxSlabToCollectionIfMissing).toHaveBeenCalledWith(
        incomeTaxSlabCollection,
        ...additionalIncomeTaxSlabs.map(expect.objectContaining)
      );
      expect(comp.incomeTaxSlabsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SecurityPermission query and add missing value', () => {
      const employee: IEmployee = { id: 456 };
      const securityPermissions: ISecurityPermission[] = [{ id: 24331 }];
      employee.securityPermissions = securityPermissions;

      const securityPermissionCollection: ISecurityPermission[] = [{ id: 91229 }];
      jest.spyOn(securityPermissionService, 'query').mockReturnValue(of(new HttpResponse({ body: securityPermissionCollection })));
      const additionalSecurityPermissions = [...securityPermissions];
      const expectedCollection: ISecurityPermission[] = [...additionalSecurityPermissions, ...securityPermissionCollection];
      jest.spyOn(securityPermissionService, 'addSecurityPermissionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(securityPermissionService.query).toHaveBeenCalled();
      expect(securityPermissionService.addSecurityPermissionToCollectionIfMissing).toHaveBeenCalledWith(
        securityPermissionCollection,
        ...additionalSecurityPermissions.map(expect.objectContaining)
      );
      expect(comp.securityPermissionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SecurityRole query and add missing value', () => {
      const employee: IEmployee = { id: 456 };
      const securityRoles: ISecurityRole[] = [{ id: 75862 }];
      employee.securityRoles = securityRoles;

      const securityRoleCollection: ISecurityRole[] = [{ id: 67455 }];
      jest.spyOn(securityRoleService, 'query').mockReturnValue(of(new HttpResponse({ body: securityRoleCollection })));
      const additionalSecurityRoles = [...securityRoles];
      const expectedCollection: ISecurityRole[] = [...additionalSecurityRoles, ...securityRoleCollection];
      jest.spyOn(securityRoleService, 'addSecurityRoleToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(securityRoleService.query).toHaveBeenCalled();
      expect(securityRoleService.addSecurityRoleToCollectionIfMissing).toHaveBeenCalledWith(
        securityRoleCollection,
        ...additionalSecurityRoles.map(expect.objectContaining)
      );
      expect(comp.securityRolesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const employee: IEmployee = { id: 456 };
      const employeeDetails: IEmployeeDetails = { id: 62296 };
      employee.employeeDetails = employeeDetails;
      const organization: IOrganization = { id: 63238 };
      employee.organization = organization;
      const incomeTaxSlab: IIncomeTaxSlab = { id: 70812 };
      employee.incomeTaxSlab = incomeTaxSlab;
      const securityPermission: ISecurityPermission = { id: 45479 };
      employee.securityPermissions = [securityPermission];
      const securityRole: ISecurityRole = { id: 38278 };
      employee.securityRoles = [securityRole];

      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      expect(comp.employeeDetailsSharedCollection).toContain(employeeDetails);
      expect(comp.organizationsSharedCollection).toContain(organization);
      expect(comp.incomeTaxSlabsSharedCollection).toContain(incomeTaxSlab);
      expect(comp.securityPermissionsSharedCollection).toContain(securityPermission);
      expect(comp.securityRolesSharedCollection).toContain(securityRole);
      expect(comp.employee).toEqual(employee);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployee>>();
      const employee = { id: 123 };
      jest.spyOn(employeeFormService, 'getEmployee').mockReturnValue(employee);
      jest.spyOn(employeeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employee }));
      saveSubject.complete();

      // THEN
      expect(employeeFormService.getEmployee).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(employeeService.update).toHaveBeenCalledWith(expect.objectContaining(employee));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployee>>();
      const employee = { id: 123 };
      jest.spyOn(employeeFormService, 'getEmployee').mockReturnValue({ id: null });
      jest.spyOn(employeeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employee: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: employee }));
      saveSubject.complete();

      // THEN
      expect(employeeFormService.getEmployee).toHaveBeenCalled();
      expect(employeeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmployee>>();
      const employee = { id: 123 };
      jest.spyOn(employeeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ employee });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(employeeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEmployeeDetails', () => {
      it('Should forward to employeeDetailsService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(employeeDetailsService, 'compareEmployeeDetails');
        comp.compareEmployeeDetails(entity, entity2);
        expect(employeeDetailsService.compareEmployeeDetails).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareOrganization', () => {
      it('Should forward to organizationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(organizationService, 'compareOrganization');
        comp.compareOrganization(entity, entity2);
        expect(organizationService.compareOrganization).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareIncomeTaxSlab', () => {
      it('Should forward to incomeTaxSlabService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(incomeTaxSlabService, 'compareIncomeTaxSlab');
        comp.compareIncomeTaxSlab(entity, entity2);
        expect(incomeTaxSlabService.compareIncomeTaxSlab).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareSecurityPermission', () => {
      it('Should forward to securityPermissionService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(securityPermissionService, 'compareSecurityPermission');
        comp.compareSecurityPermission(entity, entity2);
        expect(securityPermissionService.compareSecurityPermission).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareSecurityRole', () => {
      it('Should forward to securityRoleService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(securityRoleService, 'compareSecurityRole');
        comp.compareSecurityRole(entity, entity2);
        expect(securityRoleService.compareSecurityRole).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
