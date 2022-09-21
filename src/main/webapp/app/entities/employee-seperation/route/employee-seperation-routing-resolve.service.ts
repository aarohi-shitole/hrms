import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEmployeeSeperation } from '../employee-seperation.model';
import { EmployeeSeperationService } from '../service/employee-seperation.service';

@Injectable({ providedIn: 'root' })
export class EmployeeSeperationRoutingResolveService implements Resolve<IEmployeeSeperation | null> {
  constructor(protected service: EmployeeSeperationService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEmployeeSeperation | null | never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((employeeSeperation: HttpResponse<IEmployeeSeperation>) => {
          if (employeeSeperation.body) {
            return of(employeeSeperation.body);
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
