/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ChakabackendTestModule } from '../../../test.module';
import { InvitationDeleteDialogComponent } from 'app/entities/invitation/invitation-delete-dialog.component';
import { InvitationService } from 'app/entities/invitation/invitation.service';

describe('Component Tests', () => {
  describe('Invitation Management Delete Component', () => {
    let comp: InvitationDeleteDialogComponent;
    let fixture: ComponentFixture<InvitationDeleteDialogComponent>;
    let service: InvitationService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChakabackendTestModule],
        declarations: [InvitationDeleteDialogComponent]
      })
        .overrideTemplate(InvitationDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(InvitationDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(InvitationService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
