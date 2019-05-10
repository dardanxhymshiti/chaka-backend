/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChakabackendTestModule } from '../../../test.module';
import { ChakaUserDetailComponent } from 'app/entities/chaka-user/chaka-user-detail.component';
import { ChakaUser } from 'app/shared/model/chaka-user.model';

describe('Component Tests', () => {
  describe('ChakaUser Management Detail Component', () => {
    let comp: ChakaUserDetailComponent;
    let fixture: ComponentFixture<ChakaUserDetailComponent>;
    const route = ({ data: of({ chakaUser: new ChakaUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChakabackendTestModule],
        declarations: [ChakaUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ChakaUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChakaUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.chakaUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
