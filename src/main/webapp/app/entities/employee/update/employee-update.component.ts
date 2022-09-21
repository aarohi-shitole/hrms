import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EmployeeFormService, EmployeeFormGroup } from './employee-form.service';
import { IEmployee } from '../employee.model';
import { EmployeeService } from '../service/employee.service';
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
import { Title } from 'app/entities/enumerations/title.model';

@Component({
  selector: 'jhi-employee-update',
  templateUrl: './employee-update.component.html',
})
export class EmployeeUpdateComponent implements OnInit {
  isSaving = false;
  employee: IEmployee | null = null;
  titleValues = Object.keys(Title);

  employeeDetailsSharedCollection: IEmployeeDetails[] = [];
  organizationsSharedCollection: IOrganization[] = [];
  incomeTaxSlabsSharedCollection: IIncomeTaxSlab[] = [];
  securityPermissionsSharedCollection: ISecurityPermission[] = [];
  securityRolesSharedCollection: ISecurityRole[] = [];

  editForm: EmployeeFormGroup = this.employeeFormService.createEmployeeFormGroup();

  constructor(
    protected employeeService: EmployeeService,
    protected employeeFormService: EmployeeFormService,
    protected employeeDetailsService: EmployeeDetailsService,
    protected organizationService: OrganizationService,
    protected incomeTaxSlabService: IncomeTaxSlabService,
    protected securityPermissionService: SecurityPermissionService,
    protected securityRoleService: SecurityRoleService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEmployeeDetails = (o1: IEmployeeDetails | null, o2: IEmployeeDetails | null): boolean =>
    this.employeeDetailsService.compareEmployeeDetails(o1, o2);

  compareOrganization = (o1: IOrganization | null, o2: IOrganization | null): boolean =>
    this.organizationService.compareOrganization(o1, o2);

  compareIncomeTaxSlab = (o1: IIncomeTaxSlab | null, o2: IIncomeTaxSlab | null): boolean =>
    this.incomeTaxSlabService.compareIncomeTaxSlab(o1, o2);

  compareSecurityPermission = (o1: ISecurityPermission | null, o2: ISecurityPermission | null): boolean =>
    this.securityPermissionService.compareSecurityPermission(o1, o2);

  compareSecurityRole = (o1: ISecurityRole | null, o2: ISecurityRole | null): boolean =>
    this.securityRoleService.compareSecurityRole(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.employee = employee;
      if (employee) {
        this.updateForm(employee);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employee = this.employeeFormService.getEmployee(this.editForm);
    if (employee.id !== null) {
      this.subscribeToSaveResponse(this.employeeService.update(employee));
    } else {
      this.subscribeToSaveResponse(this.employeeService.create(employee));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(employee: IEmployee): void {
    this.employee = employee;
    this.employeeFormService.resetForm(this.editForm, employee);

    this.employeeDetailsSharedCollection = this.employeeDetailsService.addEmployeeDetailsToCollectionIfMissing<IEmployeeDetails>(
      this.employeeDetailsSharedCollection,
      employee.employeeDetails
    );
    this.organizationsSharedCollection = this.organizationService.addOrganizationToCollectionIfMissing<IOrganization>(
      this.organizationsSharedCollection,
      employee.organization
    );
    this.incomeTaxSlabsSharedCollection = this.incomeTaxSlabService.addIncomeTaxSlabToCollectionIfMissing<IIncomeTaxSlab>(
      this.incomeTaxSlabsSharedCollection,
      employee.incomeTaxSlab
    );
    this.securityPermissionsSharedCollection =
      this.securityPermissionService.addSecurityPermissionToCollectionIfMissing<ISecurityPermission>(
        this.securityPermissionsSharedCollection,
        ...(employee.securityPermissions ?? [])
      );
    this.securityRolesSharedCollection = this.securityRoleService.addSecurityRoleToCollectionIfMissing<ISecurityRole>(
      this.securityRolesSharedCollection,
      ...(employee.securityRoles ?? [])
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeeDetailsService
      .query()
      .pipe(map((res: HttpResponse<IEmployeeDetails[]>) => res.body ?? []))
      .pipe(
        map((employeeDetails: IEmployeeDetails[]) =>
          this.employeeDetailsService.addEmployeeDetailsToCollectionIfMissing<IEmployeeDetails>(
            employeeDetails,
            this.employee?.employeeDetails
          )
        )
      )
      .subscribe((employeeDetails: IEmployeeDetails[]) => (this.employeeDetailsSharedCollection = employeeDetails));

    this.organizationService
      .query()
      .pipe(map((res: HttpResponse<IOrganization[]>) => res.body ?? []))
      .pipe(
        map((organizations: IOrganization[]) =>
          this.organizationService.addOrganizationToCollectionIfMissing<IOrganization>(organizations, this.employee?.organization)
        )
      )
      .subscribe((organizations: IOrganization[]) => (this.organizationsSharedCollection = organizations));

    this.incomeTaxSlabService
      .query()
      .pipe(map((res: HttpResponse<IIncomeTaxSlab[]>) => res.body ?? []))
      .pipe(
        map((incomeTaxSlabs: IIncomeTaxSlab[]) =>
          this.incomeTaxSlabService.addIncomeTaxSlabToCollectionIfMissing<IIncomeTaxSlab>(incomeTaxSlabs, this.employee?.incomeTaxSlab)
        )
      )
      .subscribe((incomeTaxSlabs: IIncomeTaxSlab[]) => (this.incomeTaxSlabsSharedCollection = incomeTaxSlabs));

    this.securityPermissionService
      .query()
      .pipe(map((res: HttpResponse<ISecurityPermission[]>) => res.body ?? []))
      .pipe(
        map((securityPermissions: ISecurityPermission[]) =>
          this.securityPermissionService.addSecurityPermissionToCollectionIfMissing<ISecurityPermission>(
            securityPermissions,
            ...(this.employee?.securityPermissions ?? [])
          )
        )
      )
      .subscribe((securityPermissions: ISecurityPermission[]) => (this.securityPermissionsSharedCollection = securityPermissions));

    this.securityRoleService
      .query()
      .pipe(map((res: HttpResponse<ISecurityRole[]>) => res.body ?? []))
      .pipe(
        map((securityRoles: ISecurityRole[]) =>
          this.securityRoleService.addSecurityRoleToCollectionIfMissing<ISecurityRole>(
            securityRoles,
            ...(this.employee?.securityRoles ?? [])
          )
        )
      )
      .subscribe((securityRoles: ISecurityRole[]) => (this.securityRolesSharedCollection = securityRoles));
  }
}
