import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IChakaUser } from 'app/shared/model/chaka-user.model';

type EntityResponseType = HttpResponse<IChakaUser>;
type EntityArrayResponseType = HttpResponse<IChakaUser[]>;

@Injectable({ providedIn: 'root' })
export class ChakaUserService {
  public resourceUrl = SERVER_API_URL + 'api/chaka-users';

  constructor(protected http: HttpClient) {}

  create(chakaUser: IChakaUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chakaUser);
    return this.http
      .post<IChakaUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(chakaUser: IChakaUser): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(chakaUser);
    return this.http
      .put<IChakaUser>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IChakaUser>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IChakaUser[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(chakaUser: IChakaUser): IChakaUser {
    const copy: IChakaUser = Object.assign({}, chakaUser, {
      birthday: chakaUser.birthday != null && chakaUser.birthday.isValid() ? chakaUser.birthday.toJSON() : null,
      dataOfRegistration:
        chakaUser.dataOfRegistration != null && chakaUser.dataOfRegistration.isValid() ? chakaUser.dataOfRegistration.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.birthday = res.body.birthday != null ? moment(res.body.birthday) : null;
      res.body.dataOfRegistration = res.body.dataOfRegistration != null ? moment(res.body.dataOfRegistration) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((chakaUser: IChakaUser) => {
        chakaUser.birthday = chakaUser.birthday != null ? moment(chakaUser.birthday) : null;
        chakaUser.dataOfRegistration = chakaUser.dataOfRegistration != null ? moment(chakaUser.dataOfRegistration) : null;
      });
    }
    return res;
  }
}
