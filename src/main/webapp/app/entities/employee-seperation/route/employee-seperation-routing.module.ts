import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { EmployeeSeperationComponent } from '../list/employee-seperation.component';
import { EmployeeSeperationDetailComponent } from '../detail/employee-seperation-detail.component';
import { EmployeeSeperationUpdateComponent } from '../update/employee-seperation-update.component';
import { EmployeeSeperationRoutingResolveService } from './employee-seperation-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const employeeSeperationRoute: Routes = [
  {
    path: '',
    component: EmployeeSeperationComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EmployeeSeperationDetailComponent,
    resolve: {
      employeeSeperation: EmployeeSeperationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EmployeeSeperationUpdateComponent,
    resolve: {
      employeeSeperation: EmployeeSeperationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EmployeeSeperationUpdateComponent,
    resolve: {
      employeeSeperation: EmployeeSeperationRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(employeeSeperationRoute)],
  exports: [RouterModule],
})
export class EmployeeSeperationRoutingModule {}
