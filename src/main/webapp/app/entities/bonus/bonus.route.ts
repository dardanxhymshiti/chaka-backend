import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Bonus } from 'app/shared/model/bonus.model';
import { BonusService } from './bonus.service';
import { BonusComponent } from './bonus.component';
import { BonusDetailComponent } from './bonus-detail.component';
import { BonusUpdateComponent } from './bonus-update.component';
import { BonusDeletePopupComponent } from './bonus-delete-dialog.component';
import { IBonus } from 'app/shared/model/bonus.model';

@Injectable({ providedIn: 'root' })
export class BonusResolve implements Resolve<IBonus> {
  constructor(private service: BonusService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBonus> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Bonus>) => response.ok),
        map((bonus: HttpResponse<Bonus>) => bonus.body)
      );
    }
    return of(new Bonus());
  }
}

export const bonusRoute: Routes = [
  {
    path: '',
    component: BonusComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'Bonuses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BonusDetailComponent,
    resolve: {
      bonus: BonusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Bonuses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BonusUpdateComponent,
    resolve: {
      bonus: BonusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Bonuses'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BonusUpdateComponent,
    resolve: {
      bonus: BonusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Bonuses'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const bonusPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BonusDeletePopupComponent,
    resolve: {
      bonus: BonusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'Bonuses'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
