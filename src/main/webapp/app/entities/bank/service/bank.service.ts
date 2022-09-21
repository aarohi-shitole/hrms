import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IBank, NewBank } from '../bank.model';

export type PartialUpdateBank = Partial<IBank> & Pick<IBank, 'id'>;

type RestOf<T extends IBank | NewBank> = Omit<T, 'lastModified' | 'createdOn'> & {
  lastModified?: string | null;
  createdOn?: string | null;
};

export type RestBank = RestOf<IBank>;

export type NewRestBank = RestOf<NewBank>;

export type PartialUpdateRestBank = RestOf<PartialUpdateBank>;

export type EntityResponseType = HttpResponse<IBank>;
export type EntityArrayResponseType = HttpResponse<IBank[]>;

@Injectable({ providedIn: 'root' })
export class BankService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/banks');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(bank: NewBank): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bank);
    return this.http.post<RestBank>(this.resourceUrl, copy, { observe: 'response' }).pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(bank: IBank): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bank);
    return this.http
      .put<RestBank>(`${this.resourceUrl}/${this.getBankIdentifier(bank)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(bank: PartialUpdateBank): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bank);
    return this.http
      .patch<RestBank>(`${this.resourceUrl}/${this.getBankIdentifier(bank)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestBank>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestBank[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getBankIdentifier(bank: Pick<IBank, 'id'>): number {
    return bank.id;
  }

  compareBank(o1: Pick<IBank, 'id'> | null, o2: Pick<IBank, 'id'> | null): boolean {
    return o1 && o2 ? this.getBankIdentifier(o1) === this.getBankIdentifier(o2) : o1 === o2;
  }

  addBankToCollectionIfMissing<Type extends Pick<IBank, 'id'>>(
    bankCollection: Type[],
    ...banksToCheck: (Type | null | undefined)[]
  ): Type[] {
    const banks: Type[] = banksToCheck.filter(isPresent);
    if (banks.length > 0) {
      const bankCollectionIdentifiers = bankCollection.map(bankItem => this.getBankIdentifier(bankItem)!);
      const banksToAdd = banks.filter(bankItem => {
        const bankIdentifier = this.getBankIdentifier(bankItem);
        if (bankCollectionIdentifiers.includes(bankIdentifier)) {
          return false;
        }
        bankCollectionIdentifiers.push(bankIdentifier);
        return true;
      });
      return [...banksToAdd, ...bankCollection];
    }
    return bankCollection;
  }

  protected convertDateFromClient<T extends IBank | NewBank | PartialUpdateBank>(bank: T): RestOf<T> {
    return {
      ...bank,
      lastModified: bank.lastModified?.toJSON() ?? null,
      createdOn: bank.createdOn?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restBank: RestBank): IBank {
    return {
      ...restBank,
      lastModified: restBank.lastModified ? dayjs(restBank.lastModified) : undefined,
      createdOn: restBank.createdOn ? dayjs(restBank.createdOn) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestBank>): HttpResponse<IBank> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestBank[]>): HttpResponse<IBank[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
