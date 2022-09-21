import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ExitFormaltiesFormService, ExitFormaltiesFormGroup } from './exit-formalties-form.service';
import { IExitFormalties } from '../exit-formalties.model';
import { ExitFormaltiesService } from '../service/exit-formalties.service';
import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { Answers } from 'app/entities/enumerations/answers.model';
import { Status } from 'app/entities/enumerations/status.model';

@Component({
  selector: 'jhi-exit-formalties-update',
  templateUrl: './exit-formalties-update.component.html',
})
export class ExitFormaltiesUpdateComponent implements OnInit {
  isSaving = false;
  exitFormalties: IExitFormalties | null = null;
  answersValues = Object.keys(Answers);
  statusValues = Object.keys(Status);

  employeesSharedCollection: IEmployee[] = [];

  editForm: ExitFormaltiesFormGroup = this.exitFormaltiesFormService.createExitFormaltiesFormGroup();

  constructor(
    protected exitFormaltiesService: ExitFormaltiesService,
    protected exitFormaltiesFormService: ExitFormaltiesFormService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ exitFormalties }) => {
      this.exitFormalties = exitFormalties;
      if (exitFormalties) {
        this.updateForm(exitFormalties);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const exitFormalties = this.exitFormaltiesFormService.getExitFormalties(this.editForm);
    if (exitFormalties.id !== null) {
      this.subscribeToSaveResponse(this.exitFormaltiesService.update(exitFormalties));
    } else {
      this.subscribeToSaveResponse(this.exitFormaltiesService.create(exitFormalties));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IExitFormalties>>): void {
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

  protected updateForm(exitFormalties: IExitFormalties): void {
    this.exitFormalties = exitFormalties;
    this.exitFormaltiesFormService.resetForm(this.editForm, exitFormalties);

    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      exitFormalties.employee
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.exitFormalties?.employee)
        )
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));
  }
}
