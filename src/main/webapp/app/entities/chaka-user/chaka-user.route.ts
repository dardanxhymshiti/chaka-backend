import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ChakaUser } from 'app/shared/model/chaka-user.model';
import { ChakaUserService } from './chaka-user.service';
import { ChakaUserComponent } from './chaka-user.component';
import { ChakaUserDetailComponent } from './chaka-user-detail.component';
import { ChakaUserUpdateComponent } from './chaka-user-update.component';
import { ChakaUserDeletePopupComponent } from './chaka-user-delete-dialog.component';
import { IChakaUser } from 'app/shared/model/chaka-user.model';

@Injectable({ providedIn: 'root' })
export class ChakaUserResolve implements Resolve<IChakaUser> {
  constructor(private service: ChakaUserService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IChakaUser> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ChakaUser>) => response.ok),
        map((chakaUser: HttpResponse<ChakaUser>) => chakaUser.body)
      );
    }
    return of(new ChakaUser());
  }
}

export const chakaUserRoute: Routes = [
  {
    path: '',
    component: ChakaUserComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'ChakaUsers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ChakaUserDetailComponent,
    resolve: {
      chakaUser: ChakaUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ChakaUsers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ChakaUserUpdateComponent,
    resolve: {
      chakaUser: ChakaUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ChakaUsers'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ChakaUserUpdateComponent,
    resolve: {
      chakaUser: ChakaUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ChakaUsers'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const chakaUserPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ChakaUserDeletePopupComponent,
    resolve: {
      chakaUser: ChakaUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'ChakaUsers'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
