import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EmployeeSeperationDetailComponent } from './employee-seperation-detail.component';

describe('EmployeeSeperation Management Detail Component', () => {
  let comp: EmployeeSeperationDetailComponent;
  let fixture: ComponentFixture<EmployeeSeperationDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmployeeSeperationDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ employeeSeperation: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EmployeeSeperationDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EmployeeSeperationDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load employeeSeperation on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.employeeSeperation).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
