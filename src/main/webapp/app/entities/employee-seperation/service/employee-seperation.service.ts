import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IEmployeeSeperation, NewEmployeeSeperation } from '../employee-seperation.model';

export type PartialUpdateEmployeeSeperation = Partial<IEmployeeSeperation> & Pick<IEmployeeSeperation, 'id'>;

type RestOf<T extends IEmployeeSeperation | NewEmployeeSeperation> = Omit<
  T,
  'seperationDate' | 'updatedOn' | 'createdOn' | 'lastModified'
> & {
  seperationDate?: string | null;
  updatedOn?: string | null;
  createdOn?: string | null;
  lastModified?: string | null;
};

export type RestEmployeeSeperation = RestOf<IEmployeeSeperation>;

export type NewRestEmployeeSeperation = RestOf<NewEmployeeSeperation>;

export type PartialUpdateRestEmployeeSeperation = RestOf<PartialUpdateEmployeeSeperation>;

export type EntityResponseType = HttpResponse<IEmployeeSeperation>;
export type EntityArrayResponseType = HttpResponse<IEmployeeSeperation[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeSeperationService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/employee-seperations');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(employeeSeperation: NewEmployeeSeperation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeSeperation);
    return this.http
      .post<RestEmployeeSeperation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(employeeSeperation: IEmployeeSeperation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeSeperation);
    return this.http
      .put<RestEmployeeSeperation>(`${this.resourceUrl}/${this.getEmployeeSeperationIdentifier(employeeSeperation)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(employeeSeperation: PartialUpdateEmployeeSeperation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(employeeSeperation);
    return this.http
      .patch<RestEmployeeSeperation>(`${this.resourceUrl}/${this.getEmployeeSeperationIdentifier(employeeSeperation)}`, copy, {
        observe: 'response',
      })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestEmployeeSeperation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestEmployeeSeperation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getEmployeeSeperationIdentifier(employeeSeperation: Pick<IEmployeeSeperation, 'id'>): number {
    return employeeSeperation.id;
  }

  compareEmployeeSeperation(o1: Pick<IEmployeeSeperation, 'id'> | null, o2: Pick<IEmployeeSeperation, 'id'> | null): boolean {
    return o1 && o2 ? this.getEmployeeSeperationIdentifier(o1) === this.getEmployeeSeperationIdentifier(o2) : o1 === o2;
  }

  addEmployeeSeperationToCollectionIfMissing<Type extends Pick<IEmployeeSeperation, 'id'>>(
    employeeSeperationCollection: Type[],
    ...employeeSeperationsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const employeeSeperations: Type[] = employeeSeperationsToCheck.filter(isPresent);
    if (employeeSeperations.length > 0) {
      const employeeSeperationCollectionIdentifiers = employeeSeperationCollection.map(
        employeeSeperationItem => this.getEmployeeSeperationIdentifier(employeeSeperationItem)!
      );
      const employeeSeperationsToAdd = employeeSeperations.filter(employeeSeperationItem => {
        const employeeSeperationIdentifier = this.getEmployeeSeperationIdentifier(employeeSeperationItem);
        if (employeeSeperationCollectionIdentifiers.includes(employeeSeperationIdentifier)) {
          return false;
        }
        employeeSeperationCollectionIdentifiers.push(employeeSeperationIdentifier);
        return true;
      });
      return [...employeeSeperationsToAdd, ...employeeSeperationCollection];
    }
    return employeeSeperationCollection;
  }

  protected convertDateFromClient<T extends IEmployeeSeperation | NewEmployeeSeperation | PartialUpdateEmployeeSeperation>(
    employeeSeperation: T
  ): RestOf<T> {
    return {
      ...employeeSeperation,
      seperationDate: employeeSeperation.seperationDate?.toJSON() ?? null,
      updatedOn: employeeSeperation.updatedOn?.toJSON() ?? null,
      createdOn: employeeSeperation.createdOn?.toJSON() ?? null,
      lastModified: employeeSeperation.lastModified?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restEmployeeSeperation: RestEmployeeSeperation): IEmployeeSeperation {
    return {
      ...restEmployeeSeperation,
      seperationDate: restEmployeeSeperation.seperationDate ? dayjs(restEmployeeSeperation.seperationDate) : undefined,
      updatedOn: restEmployeeSeperation.updatedOn ? dayjs(restEmployeeSeperation.updatedOn) : undefined,
      createdOn: restEmployeeSeperation.createdOn ? dayjs(restEmployeeSeperation.createdOn) : undefined,
      lastModified: restEmployeeSeperation.lastModified ? dayjs(restEmployeeSeperation.lastModified) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestEmployeeSeperation>): HttpResponse<IEmployeeSeperation> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestEmployeeSeperation[]>): HttpResponse<IEmployeeSeperation[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
