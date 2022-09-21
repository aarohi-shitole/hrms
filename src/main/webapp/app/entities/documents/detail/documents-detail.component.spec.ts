import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DocumentsDetailComponent } from './documents-detail.component';

describe('Documents Management Detail Component', () => {
  let comp: DocumentsDetailComponent;
  let fixture: ComponentFixture<DocumentsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DocumentsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ documents: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DocumentsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DocumentsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load documents on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.documents).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
