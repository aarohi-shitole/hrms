import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { EmployeeDetailsFormService, EmployeeDetailsFormGroup } from './employee-details-form.service';
import { IEmployeeDetails } from '../employee-details.model';
import { EmployeeDetailsService } from '../service/employee-details.service';
import { BloodGroup } from 'app/entities/enumerations/blood-group.model';
import { MaritalStatus } from 'app/entities/enumerations/marital-status.model';

@Component({
  selector: 'jhi-employee-details-update',
  templateUrl: './employee-details-update.component.html',
})
export class EmployeeDetailsUpdateComponent implements OnInit {
  isSaving = false;
  employeeDetails: IEmployeeDetails | null = null;
  bloodGroupValues = Object.keys(BloodGroup);
  maritalStatusValues = Object.keys(MaritalStatus);

  editForm: EmployeeDetailsFormGroup = this.employeeDetailsFormService.createEmployeeDetailsFormGroup();

  constructor(
    protected employeeDetailsService: EmployeeDetailsService,
    protected employeeDetailsFormService: EmployeeDetailsFormService,
    protected activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeDetails }) => {
      this.employeeDetails = employeeDetails;
      if (employeeDetails) {
        this.updateForm(employeeDetails);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const employeeDetails = this.employeeDetailsFormService.getEmployeeDetails(this.editForm);
    if (employeeDetails.id !== null) {
      this.subscribeToSaveResponse(this.employeeDetailsService.update(employeeDetails));
    } else {
      this.subscribeToSaveResponse(this.employeeDetailsService.create(employeeDetails));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployeeDetails>>): void {
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

  protected updateForm(employeeDetails: IEmployeeDetails): void {
    this.employeeDetails = employeeDetails;
    this.employeeDetailsFormService.resetForm(this.editForm, employeeDetails);
  }
}
