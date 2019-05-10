import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Invitation } from 'app/shared/model/invitation.model';
import { InvitationService } from './invitation.service';
import { InvitationComponent } from './invitation.component';
import { InvitationDetailComponent } from './invitation-detail.component';
import { InvitationUpdateComponent } from './invitation-update.component';
import { InvitationDeletePopupComponent } from './invitation-delete-dialog.component';
import { IInvitation } from 'app/shared/model/invitation.model';

@Injectable({ providedIn: 'root' })
export class InvitationResolve implements Resolve<IInvitation> {
  constructor(private service: InvitationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInvitation> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Invitation>) => response.ok),
        map((invitation: HttpResponse<Invitation>) => invitation.body)
      );
    }
    return of(new Invitation());
  }
}

export const invitationRoute: Routes = [
  {
    path: '',
    component: InvitationComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Invitations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InvitationDetailComponent,
    resolve: {
      invitation: InvitationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Invitations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InvitationUpdateComponent,
    resolve: {
      invitation: InvitationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Invitations'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InvitationUpdateComponent,
    resolve: {
      invitation: InvitationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Invitations'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const invitationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InvitationDeletePopupComponent,
    resolve: {
      invitation: InvitationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Invitations'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
