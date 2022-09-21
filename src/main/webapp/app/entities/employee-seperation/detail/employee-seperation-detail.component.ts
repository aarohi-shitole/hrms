import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEmployeeSeperation } from '../employee-seperation.model';

@Component({
  selector: 'jhi-employee-seperation-detail',
  templateUrl: './employee-seperation-detail.component.html',
})
export class EmployeeSeperationDetailComponent implements OnInit {
  employeeSeperation: IEmployeeSeperation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ employeeSeperation }) => {
      this.employeeSeperation = employeeSeperation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
