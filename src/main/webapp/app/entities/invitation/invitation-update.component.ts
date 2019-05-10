import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IInvitation, Invitation } from 'app/shared/model/invitation.model';
import { InvitationService } from './invitation.service';
import { IChakaUser } from 'app/shared/model/chaka-user.model';
import { ChakaUserService } from 'app/entities/chaka-user';

@Component({
  selector: 'jhi-invitation-update',
  templateUrl: './invitation-update.component.html'
})
export class InvitationUpdateComponent implements OnInit {
  invitation: IInvitation;
  isSaving: boolean;

  chakausers: IChakaUser[];

  editForm = this.fb.group({
    id: [],
    invitationID: [],
    userID: [],
    email: [],
    status: [],
    chakaUser: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected invitationService: InvitationService,
    protected chakaUserService: ChakaUserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ invitation }) => {
      this.updateForm(invitation);
      this.invitation = invitation;
    });
    this.chakaUserService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IChakaUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IChakaUser[]>) => response.body)
      )
      .subscribe((res: IChakaUser[]) => (this.chakausers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(invitation: IInvitation) {
    this.editForm.patchValue({
      id: invitation.id,
      invitationID: invitation.invitationID,
      userID: invitation.userID,
      email: invitation.email,
      status: invitation.status,
      chakaUser: invitation.chakaUser
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const invitation = this.createFromForm();
    if (invitation.id !== undefined) {
      this.subscribeToSaveResponse(this.invitationService.update(invitation));
    } else {
      this.subscribeToSaveResponse(this.invitationService.create(invitation));
    }
  }

  private createFromForm(): IInvitation {
    const entity = {
      ...new Invitation(),
      id: this.editForm.get(['id']).value,
      invitationID: this.editForm.get(['invitationID']).value,
      userID: this.editForm.get(['userID']).value,
      email: this.editForm.get(['email']).value,
      status: this.editForm.get(['status']).value,
      chakaUser: this.editForm.get(['chakaUser']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInvitation>>) {
    result.subscribe((res: HttpResponse<IInvitation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
