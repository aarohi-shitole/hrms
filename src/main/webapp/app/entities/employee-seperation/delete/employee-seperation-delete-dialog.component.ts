import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IEmployeeSeperation } from '../employee-seperation.model';
import { EmployeeSeperationService } from '../service/employee-seperation.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './employee-seperation-delete-dialog.component.html',
})
export class EmployeeSeperationDeleteDialogComponent {
  employeeSeperation?: IEmployeeSeperation;

  constructor(protected employeeSeperationService: EmployeeSeperationService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.employeeSeperationService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
