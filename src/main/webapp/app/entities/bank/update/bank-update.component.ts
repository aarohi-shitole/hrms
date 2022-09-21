import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { BankFormService, BankFormGroup } from './bank-form.service';
import { IBank } from '../bank.model';
import { BankService } from '../service/bank.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { IOrganization } from 'app/entities/organization/organization.model';
import { OrganizationService } from 'app/entities/organization/service/organization.service';

@Component({
  selector: 'jhi-bank-update',
  templateUrl: './bank-update.component.html',
})
export class BankUpdateComponent implements OnInit {
  isSaving = false;
  bank: IBank | null = null;

  employeesSharedCollection: IEmployee[] = [];
  organizationsSharedCollection: IOrganization[] = [];

  editForm: BankFormGroup = this.bankFormService.createBankFormGroup();

  constructor(
    protected bankService: BankService,
    protected bankFormService: BankFormService,
    protected employeeService: EmployeeService,
    protected organizationService: OrganizationService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  compareOrganization = (o1: IOrganization | null, o2: IOrganization | null): boolean =>
    this.organizationService.compareOrganization(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ bank }) => {
      this.bank = bank;
      if (bank) {
        this.updateForm(bank);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const bank = this.bankFormService.getBank(this.editForm);
    if (bank.id !== null) {
      this.subscribeToSaveResponse(this.bankService.update(bank));
    } else {
      this.subscribeToSaveResponse(this.bankService.create(bank));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBank>>): void {
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

  protected updateForm(bank: IBank): void {
    this.bank = bank;
    this.bankFormService.resetForm(this.editForm, bank);

    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      bank.employee
    );
    this.organizationsSharedCollection = this.organizationService.addOrganizationToCollectionIfMissing<IOrganization>(
      this.organizationsSharedCollection,
      bank.organization
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) => this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.bank?.employee))
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));

    this.organizationService
      .query()
      .pipe(map((res: HttpResponse<IOrganization[]>) => res.body ?? []))
      .pipe(
        map((organizations: IOrganization[]) =>
          this.organizationService.addOrganizationToCollectionIfMissing<IOrganization>(organizations, this.bank?.organization)
        )
      )
      .subscribe((organizations: IOrganization[]) => (this.organizationsSharedCollection = organizations));
  }
}
