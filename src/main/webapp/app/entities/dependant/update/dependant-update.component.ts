import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { DependantFormService, DependantFormGroup } from './dependant-form.service';
import { IDependant } from '../dependant.model';
import { DependantService } from '../service/dependant.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { DependantType } from 'app/entities/enumerations/dependant-type.model';

@Component({
  selector: 'jhi-dependant-update',
  templateUrl: './dependant-update.component.html',
})
export class DependantUpdateComponent implements OnInit {
  isSaving = false;
  dependant: IDependant | null = null;
  dependantTypeValues = Object.keys(DependantType);

  employeesSharedCollection: IEmployee[] = [];

  editForm: DependantFormGroup = this.dependantFormService.createDependantFormGroup();

  constructor(
    protected dependantService: DependantService,
    protected dependantFormService: DependantFormService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dependant }) => {
      this.dependant = dependant;
      if (dependant) {
        this.updateForm(dependant);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dependant = this.dependantFormService.getDependant(this.editForm);
    if (dependant.id !== null) {
      this.subscribeToSaveResponse(this.dependantService.update(dependant));
    } else {
      this.subscribeToSaveResponse(this.dependantService.create(dependant));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDependant>>): void {
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

  protected updateForm(dependant: IDependant): void {
    this.dependant = dependant;
    this.dependantFormService.resetForm(this.editForm, dependant);

    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      dependant.employee
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.dependant?.employee)
        )
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));
  }
}
