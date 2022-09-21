import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { ExitFormaltiesComponent } from './list/exit-formalties.component';
import { ExitFormaltiesDetailComponent } from './detail/exit-formalties-detail.component';
import { ExitFormaltiesUpdateComponent } from './update/exit-formalties-update.component';
import { ExitFormaltiesDeleteDialogComponent } from './delete/exit-formalties-delete-dialog.component';
import { ExitFormaltiesRoutingModule } from './route/exit-formalties-routing.module';

@NgModule({
  imports: [SharedModule, ExitFormaltiesRoutingModule],
  declarations: [
    ExitFormaltiesComponent,
    ExitFormaltiesDetailComponent,
    ExitFormaltiesUpdateComponent,
    ExitFormaltiesDeleteDialogComponent,
  ],
})
export class ExitFormaltiesModule {}
