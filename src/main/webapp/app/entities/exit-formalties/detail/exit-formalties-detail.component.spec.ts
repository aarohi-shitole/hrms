import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ExitFormaltiesDetailComponent } from './exit-formalties-detail.component';

describe('ExitFormalties Management Detail Component', () => {
  let comp: ExitFormaltiesDetailComponent;
  let fixture: ComponentFixture<ExitFormaltiesDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ExitFormaltiesDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ exitFormalties: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ExitFormaltiesDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ExitFormaltiesDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load exitFormalties on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.exitFormalties).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
