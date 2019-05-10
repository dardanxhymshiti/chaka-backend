/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ChakabackendTestModule } from '../../../test.module';
import { ChakaUserUpdateComponent } from 'app/entities/chaka-user/chaka-user-update.component';
import { ChakaUserService } from 'app/entities/chaka-user/chaka-user.service';
import { ChakaUser } from 'app/shared/model/chaka-user.model';

describe('Component Tests', () => {
  describe('ChakaUser Management Update Component', () => {
    let comp: ChakaUserUpdateComponent;
    let fixture: ComponentFixture<ChakaUserUpdateComponent>;
    let service: ChakaUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ChakabackendTestModule],
        declarations: [ChakaUserUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ChakaUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChakaUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ChakaUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ChakaUser(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ChakaUser();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
