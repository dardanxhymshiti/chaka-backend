import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChakaUser } from 'app/shared/model/chaka-user.model';

@Component({
  selector: 'jhi-chaka-user-detail',
  templateUrl: './chaka-user-detail.component.html'
})
export class ChakaUserDetailComponent implements OnInit {
  chakaUser: IChakaUser;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ chakaUser }) => {
      this.chakaUser = chakaUser;
    });
  }

  previousState() {
    window.history.back();
  }
}
