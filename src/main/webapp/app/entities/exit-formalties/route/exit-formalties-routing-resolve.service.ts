import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IExitFormalties } from '../exit-formalties.model';
import { ExitFormaltiesService } from '../service/exit-formalties.service';

@Injectable({ providedIn: 'root' })
export class ExitFormaltiesRoutingResolveService implements Resolve<IExitFormalties | null> {
  constructor(protected service: ExitFormaltiesService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IExitFormalties | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((exitFormalties: HttpResponse<IExitFormalties>) => {
          if (exitFormalties.body) {
            return of(exitFormalties.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(null);
  }
}
