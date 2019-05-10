/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChakabackendTestModule } from '../../../test.module';
import { InvitationDetailComponent } from 'app/entities/invitation/invitation-detail.component';
import { Invitation } from 'app/shared/model/invitation.model';

describe('Component Tests', () => {
  describe('Invitation Management Detail Component', () => {
    let comp: InvitationDetailComponent;
    let fixture: ComponentFixture<InvitationDetailComponent>;
    const route = ({ data: of({ invitation: new Invitation(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChakabackendTestModule],
        declarations: [InvitationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(InvitationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvitationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.invitation).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
