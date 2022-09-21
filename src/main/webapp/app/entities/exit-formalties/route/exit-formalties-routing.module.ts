import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ExitFormaltiesComponent } from '../list/exit-formalties.component';
import { ExitFormaltiesDetailComponent } from '../detail/exit-formalties-detail.component';
import { ExitFormaltiesUpdateComponent } from '../update/exit-formalties-update.component';
import { ExitFormaltiesRoutingResolveService } from './exit-formalties-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const exitFormaltiesRoute: Routes = [
  {
    path: '',
    component: ExitFormaltiesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ExitFormaltiesDetailComponent,
    resolve: {
      exitFormalties: ExitFormaltiesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ExitFormaltiesUpdateComponent,
    resolve: {
      exitFormalties: ExitFormaltiesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ExitFormaltiesUpdateComponent,
    resolve: {
      exitFormalties: ExitFormaltiesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(exitFormaltiesRoute)],
  exports: [RouterModule],
})
export class ExitFormaltiesRoutingModule {}
