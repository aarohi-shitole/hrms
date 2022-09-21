jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { EmployeeSeperationService } from '../service/employee-seperation.service';

import { EmployeeSeperationDeleteDialogComponent } from './employee-seperation-delete-dialog.component';

describe('EmployeeSeperation Management Delete Component', () => {
  let comp: EmployeeSeperationDeleteDialogComponent;
  let fixture: ComponentFixture<EmployeeSeperationDeleteDialogComponent>;
  let service: EmployeeSeperationService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [EmployeeSeperationDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(EmployeeSeperationDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EmployeeSeperationDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(EmployeeSeperationService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
