import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { EmployeeSeperationFormService, EmployeeSeperationFormGroup } from './employee-seperation-form.service';
import { IEmployeeSeperation } from '../employee-seperation.model';
import { EmployeeSeperationService } from '../service/employee-seperation.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { SeperationStatus } from 'app/entities/enumerations/seperation-status.model';

@Component({
  selector: 'jhi-employee-seperation-update',
  templateUrl: './employee-seperation-update.component.html',
})
export class EmployeeSeperationUpdateComponent implements OnInit {
  isSaving = false;
  employeeSeperation: IEmployeeSeperation | null = null;
  seperationStatusValues = Object.keys(SeperationStatus);

  employeesSharedCollection: IEmployee[] = [];

  editForm: EmployeeSeperationFormGroup = this.employeeSeperationFormService.createEmployeeSeperationFormGroup();

  constructor(
    protected employeeSeperationService: EmployeeSeperationService,
    protected employeeSeperationFormService: EmployeeSeperationFormService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeSeperation }) => {
      this.employeeSeperation = employeeSeperation;
      if (employeeSeperation) {
        this.updateForm(employeeSeperation);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employeeSeperation = this.employeeSeperationFormService.getEmployeeSeperation(this.editForm);
    if (employeeSeperation.id !== null) {
      this.subscribeToSaveResponse(this.employeeSeperationService.update(employeeSeperation));
    } else {
      this.subscribeToSaveResponse(this.employeeSeperationService.create(employeeSeperation));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeSeperation>>): void {
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

  protected updateForm(employeeSeperation: IEmployeeSeperation): void {
    this.employeeSeperation = employeeSeperation;
    this.employeeSeperationFormService.resetForm(this.editForm, employeeSeperation);

    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      employeeSeperation.employee
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.employeeSeperation?.employee)
        )
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));
  }
}
