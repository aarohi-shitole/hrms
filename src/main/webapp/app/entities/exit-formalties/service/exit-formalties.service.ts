import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IExitFormalties, NewExitFormalties } from '../exit-formalties.model';

export type PartialUpdateExitFormalties = Partial<IExitFormalties> & Pick<IExitFormalties, 'id'>;

type RestOf<T extends IExitFormalties | NewExitFormalties> = Omit<T, 'exitDate' | 'lastModified' | 'createdOn'> & {
  exitDate?: string | null;
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestExitFormalties = RestOf<IExitFormalties>;

export type NewRestExitFormalties = RestOf<NewExitFormalties>;

export type PartialUpdateRestExitFormalties = RestOf<PartialUpdateExitFormalties>;

export type EntityResponseType = HttpResponse<IExitFormalties>;
export type EntityArrayResponseType = HttpResponse<IExitFormalties[]>;

@Injectable({ providedIn: 'root' })
export class ExitFormaltiesService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/exit-formalties');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(exitFormalties: NewExitFormalties): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(exitFormalties);
    return this.http
      .post<RestExitFormalties>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(exitFormalties: IExitFormalties): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(exitFormalties);
    return this.http
      .put<RestExitFormalties>(`${this.resourceUrl}/${this.getExitFormaltiesIdentifier(exitFormalties)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(exitFormalties: PartialUpdateExitFormalties): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(exitFormalties);
    return this.http
      .patch<RestExitFormalties>(`${this.resourceUrl}/${this.getExitFormaltiesIdentifier(exitFormalties)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestExitFormalties>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestExitFormalties[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getExitFormaltiesIdentifier(exitFormalties: Pick<IExitFormalties, 'id'>): number {
    return exitFormalties.id;
  }

  compareExitFormalties(o1: Pick<IExitFormalties, 'id'> | null, o2: Pick<IExitFormalties, 'id'> | null): boolean {
    return o1 && o2 ? this.getExitFormaltiesIdentifier(o1) === this.getExitFormaltiesIdentifier(o2) : o1 === o2;
  }

  addExitFormaltiesToCollectionIfMissing<Type extends Pick<IExitFormalties, 'id'>>(
    exitFormaltiesCollection: Type[],
    ...exitFormaltiesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const exitFormalties: Type[] = exitFormaltiesToCheck.filter(isPresent);
    if (exitFormalties.length > 0) {
      const exitFormaltiesCollectionIdentifiers = exitFormaltiesCollection.map(
        exitFormaltiesItem => this.getExitFormaltiesIdentifier(exitFormaltiesItem)!
      );
      const exitFormaltiesToAdd = exitFormalties.filter(exitFormaltiesItem => {
        const exitFormaltiesIdentifier = this.getExitFormaltiesIdentifier(exitFormaltiesItem);
        if (exitFormaltiesCollectionIdentifiers.includes(exitFormaltiesIdentifier)) {
          return false;
        }
        exitFormaltiesCollectionIdentifiers.push(exitFormaltiesIdentifier);
        return true;
      });
      return [...exitFormaltiesToAdd, ...exitFormaltiesCollection];
    }
    return exitFormaltiesCollection;
  }

  protected convertDateFromClient<T extends IExitFormalties | NewExitFormalties | PartialUpdateExitFormalties>(
    exitFormalties: T
  ): RestOf<T> {
    return {
      ...exitFormalties,
      exitDate: exitFormalties.exitDate?.toJSON() ?? null,
      lastModified: exitFormalties.lastModified?.toJSON() ?? null,
      createdOn: exitFormalties.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restExitFormalties: RestExitFormalties): IExitFormalties {
    return {
      ...restExitFormalties,
      exitDate: restExitFormalties.exitDate ? dayjs(restExitFormalties.exitDate) : undefined,
      lastModified: restExitFormalties.lastModified ? dayjs(restExitFormalties.lastModified) : undefined,
      createdOn: restExitFormalties.createdOn ? dayjs(restExitFormalties.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestExitFormalties>): HttpResponse<IExitFormalties> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestExitFormalties[]>): HttpResponse<IExitFormalties[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
