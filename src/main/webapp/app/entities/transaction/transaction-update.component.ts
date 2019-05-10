import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ITransaction, Transaction } from 'app/shared/model/transaction.model';
import { TransactionService } from './transaction.service';
import { IChakaUser } from 'app/shared/model/chaka-user.model';
import { ChakaUserService } from 'app/entities/chaka-user';

@Component({
  selector: 'jhi-transaction-update',
  templateUrl: './transaction-update.component.html'
})
export class TransactionUpdateComponent implements OnInit {
  transaction: ITransaction;
  isSaving: boolean;

  chakausers: IChakaUser[];

  editForm = this.fb.group({
    id: [],
    transactionID: [],
    userID: [],
    description: [],
    type: [],
    total: [],
    chakaUser: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected transactionService: TransactionService,
    protected chakaUserService: ChakaUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transaction }) => {
      this.updateForm(transaction);
      this.transaction = transaction;
    });
    this.chakaUserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IChakaUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IChakaUser[]>) => response.body)
      )
      .subscribe((res: IChakaUser[]) => (this.chakausers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(transaction: ITransaction) {
    this.editForm.patchValue({
      id: transaction.id,
      transactionID: transaction.transactionID,
      userID: transaction.userID,
      description: transaction.description,
      type: transaction.type,
      total: transaction.total,
      chakaUser: transaction.chakaUser
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transaction = this.createFromForm();
    if (transaction.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionService.update(transaction));
    } else {
      this.subscribeToSaveResponse(this.transactionService.create(transaction));
    }
  }

  private createFromForm(): ITransaction {
    const entity = {
      ...new Transaction(),
      id: this.editForm.get(['id']).value,
      transactionID: this.editForm.get(['transactionID']).value,
      userID: this.editForm.get(['userID']).value,
      description: this.editForm.get(['description']).value,
      type: this.editForm.get(['type']).value,
      total: this.editForm.get(['total']).value,
      chakaUser: this.editForm.get(['chakaUser']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaction>>) {
    result.subscribe((res: HttpResponse<ITransaction>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackChakaUserById(index: number, item: IChakaUser) {
    return item.id;
  }
}
