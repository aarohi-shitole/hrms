import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IExitFormalties } from '../exit-formalties.model';
import { ExitFormaltiesService } from '../service/exit-formalties.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './exit-formalties-delete-dialog.component.html',
})
export class ExitFormaltiesDeleteDialogComponent {
  exitFormalties?: IExitFormalties;

  constructor(protected exitFormaltiesService: ExitFormaltiesService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.exitFormaltiesService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
