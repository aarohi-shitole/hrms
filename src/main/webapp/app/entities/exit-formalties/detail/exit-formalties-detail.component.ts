import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IExitFormalties } from '../exit-formalties.model';

@Component({
  selector: 'jhi-exit-formalties-detail',
  templateUrl: './exit-formalties-detail.component.html',
})
export class ExitFormaltiesDetailComponent implements OnInit {
  exitFormalties: IExitFormalties | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ exitFormalties }) => {
      this.exitFormalties = exitFormalties;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
