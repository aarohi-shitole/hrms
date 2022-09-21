import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { EmployeeSeperationComponent } from './list/employee-seperation.component';
import { EmployeeSeperationDetailComponent } from './detail/employee-seperation-detail.component';
import { EmployeeSeperationUpdateComponent } from './update/employee-seperation-update.component';
import { EmployeeSeperationDeleteDialogComponent } from './delete/employee-seperation-delete-dialog.component';
import { EmployeeSeperationRoutingModule } from './route/employee-seperation-routing.module';

@NgModule({
  imports: [SharedModule, EmployeeSeperationRoutingModule],
  declarations: [
    EmployeeSeperationComponent,
    EmployeeSeperationDetailComponent,
    EmployeeSeperationUpdateComponent,
    EmployeeSeperationDeleteDialogComponent,
  ],
})
export class EmployeeSeperationModule {}
