import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBonus, Bonus } from 'app/shared/model/bonus.model';
import { BonusService } from './bonus.service';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { UserAccountService } from 'app/entities/user-account';

@Component({
  selector: 'jhi-bonus-update',
  templateUrl: './bonus-update.component.html'
})
export class BonusUpdateComponent implements OnInit {
  bonus: IBonus;
  isSaving: boolean;

  useraccounts: IUserAccount[];

  editForm = this.fb.group({
    id: [],
    bonusID: [],
    userID: [],
    coins: [],
    userAccount: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected bonusService: BonusService,
    protected userAccountService: UserAccountService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ bonus }) => {
      this.updateForm(bonus);
      this.bonus = bonus;
    });
    this.userAccountService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUserAccount[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUserAccount[]>) => response.body)
      )
      .subscribe((res: IUserAccount[]) => (this.useraccounts = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(bonus: IBonus) {
    this.editForm.patchValue({
      id: bonus.id,
      bonusID: bonus.bonusID,
      userID: bonus.userID,
      coins: bonus.coins,
      userAccount: bonus.userAccount
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const bonus = this.createFromForm();
    if (bonus.id !== undefined) {
      this.subscribeToSaveResponse(this.bonusService.update(bonus));
    } else {
      this.subscribeToSaveResponse(this.bonusService.create(bonus));
    }
  }

  private createFromForm(): IBonus {
    const entity = {
      ...new Bonus(),
      id: this.editForm.get(['id']).value,
      bonusID: this.editForm.get(['bonusID']).value,
      userID: this.editForm.get(['userID']).value,
      coins: this.editForm.get(['coins']).value,
      userAccount: this.editForm.get(['userAccount']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBonus>>) {
    result.subscribe((res: HttpResponse<IBonus>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
