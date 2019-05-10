import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IUserAccount, UserAccount } from 'app/shared/model/user-account.model';
import { UserAccountService } from './user-account.service';

@Component({
  selector: 'jhi-user-account-update',
  templateUrl: './user-account-update.component.html'
})
export class UserAccountUpdateComponent implements OnInit {
  userAccount: IUserAccount;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    accountID: [],
    userID: [],
    bilane: [],
    status: []
  });

  constructor(protected userAccountService: UserAccountService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ userAccount }) => {
      this.updateForm(userAccount);
      this.userAccount = userAccount;
    });
  }

  updateForm(userAccount: IUserAccount) {
    this.editForm.patchValue({
      id: userAccount.id,
      accountID: userAccount.accountID,
      userID: userAccount.userID,
      bilane: userAccount.bilane,
      status: userAccount.status
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const userAccount = this.createFromForm();
    if (userAccount.id !== undefined) {
      this.subscribeToSaveResponse(this.userAccountService.update(userAccount));
    } else {
      this.subscribeToSaveResponse(this.userAccountService.create(userAccount));
    }
  }

  private createFromForm(): IUserAccount {
    const entity = {
      ...new UserAccount(),
      id: this.editForm.get(['id']).value,
      accountID: this.editForm.get(['accountID']).value,
      userID: this.editForm.get(['userID']).value,
      bilane: this.editForm.get(['bilane']).value,
      status: this.editForm.get(['status']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserAccount>>) {
    result.subscribe((res: HttpResponse<IUserAccount>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
