import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IChakaUser, ChakaUser } from 'app/shared/model/chaka-user.model';
import { ChakaUserService } from './chaka-user.service';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { UserAccountService } from 'app/entities/user-account';

@Component({
  selector: 'jhi-chaka-user-update',
  templateUrl: './chaka-user-update.component.html'
})
export class ChakaUserUpdateComponent implements OnInit {
  chakaUser: IChakaUser;
  isSaving: boolean;

  userids: IUserAccount[];

  editForm = this.fb.group({
    id: [],
    userID: [],
    name: [],
    surname: [],
    birthday: [],
    username: [],
    password: [],
    country: [],
    city: [],
    zip: [],
    street: [],
    email: [],
    phone: [],
    language: [],
    dataOfRegistration: [],
    userID: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected chakaUserService: ChakaUserService,
    protected userAccountService: UserAccountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ chakaUser }) => {
      this.updateForm(chakaUser);
      this.chakaUser = chakaUser;
    });
    this.userAccountService
      .query({ filter: 'chakauser-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<IUserAccount[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUserAccount[]>) => response.body)
      )
      .subscribe(
        (res: IUserAccount[]) => {
          if (!this.chakaUser.userID || !this.chakaUser.userID.id) {
            this.userids = res;
          } else {
            this.userAccountService
              .find(this.chakaUser.userID.id)
              .pipe(
                filter((subResMayBeOk: HttpResponse<IUserAccount>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<IUserAccount>) => subResponse.body)
              )
              .subscribe(
                (subRes: IUserAccount) => (this.userids = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(chakaUser: IChakaUser) {
    this.editForm.patchValue({
      id: chakaUser.id,
      userID: chakaUser.userID,
      name: chakaUser.name,
      surname: chakaUser.surname,
      birthday: chakaUser.birthday != null ? chakaUser.birthday.format(DATE_TIME_FORMAT) : null,
      username: chakaUser.username,
      password: chakaUser.password,
      country: chakaUser.country,
      city: chakaUser.city,
      zip: chakaUser.zip,
      street: chakaUser.street,
      email: chakaUser.email,
      phone: chakaUser.phone,
      language: chakaUser.language,
      dataOfRegistration: chakaUser.dataOfRegistration != null ? chakaUser.dataOfRegistration.format(DATE_TIME_FORMAT) : null,
      userID: chakaUser.userID
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const chakaUser = this.createFromForm();
    if (chakaUser.id !== undefined) {
      this.subscribeToSaveResponse(this.chakaUserService.update(chakaUser));
    } else {
      this.subscribeToSaveResponse(this.chakaUserService.create(chakaUser));
    }
  }

  private createFromForm(): IChakaUser {
    const entity = {
      ...new ChakaUser(),
      id: this.editForm.get(['id']).value,
      userID: this.editForm.get(['userID']).value,
      name: this.editForm.get(['name']).value,
      surname: this.editForm.get(['surname']).value,
      birthday: this.editForm.get(['birthday']).value != null ? moment(this.editForm.get(['birthday']).value, DATE_TIME_FORMAT) : undefined,
      username: this.editForm.get(['username']).value,
      password: this.editForm.get(['password']).value,
      country: this.editForm.get(['country']).value,
      city: this.editForm.get(['city']).value,
      zip: this.editForm.get(['zip']).value,
      street: this.editForm.get(['street']).value,
      email: this.editForm.get(['email']).value,
      phone: this.editForm.get(['phone']).value,
      language: this.editForm.get(['language']).value,
      dataOfRegistration:
        this.editForm.get(['dataOfRegistration']).value != null
          ? moment(this.editForm.get(['dataOfRegistration']).value, DATE_TIME_FORMAT)
          : undefined,
      userID: this.editForm.get(['userID']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChakaUser>>) {
    result.subscribe((res: HttpResponse<IChakaUser>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackUserAccountById(index: number, item: IUserAccount) {
    return item.id;
  }
}
