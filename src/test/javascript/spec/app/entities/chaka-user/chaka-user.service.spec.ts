/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ChakaUserService } from 'app/entities/chaka-user/chaka-user.service';
import { IChakaUser, ChakaUser } from 'app/shared/model/chaka-user.model';

describe('Service Tests', () => {
  describe('ChakaUser Service', () => {
    let injector: TestBed;
    let service: ChakaUserService;
    let httpMock: HttpTestingController;
    let elemDefault: IChakaUser;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(ChakaUserService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new ChakaUser(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            birthday: currentDate.format(DATE_TIME_FORMAT),
            dataOfRegistration: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a ChakaUser', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            birthday: currentDate.format(DATE_TIME_FORMAT),
            dataOfRegistration: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            birthday: currentDate,
            dataOfRegistration: currentDate
          },
          returnedFromService
        );
        service
          .create(new ChakaUser(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a ChakaUser', async () => {
        const returnedFromService = Object.assign(
          {
            userID: 'BBBBBB',
            name: 'BBBBBB',
            surname: 'BBBBBB',
            birthday: currentDate.format(DATE_TIME_FORMAT),
            username: 'BBBBBB',
            password: 'BBBBBB',
            country: 'BBBBBB',
            city: 'BBBBBB',
            zip: 'BBBBBB',
            street: 'BBBBBB',
            email: 'BBBBBB',
            phone: 'BBBBBB',
            language: 'BBBBBB',
            dataOfRegistration: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            birthday: currentDate,
            dataOfRegistration: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of ChakaUser', async () => {
        const returnedFromService = Object.assign(
          {
            userID: 'BBBBBB',
            name: 'BBBBBB',
            surname: 'BBBBBB',
            birthday: currentDate.format(DATE_TIME_FORMAT),
            username: 'BBBBBB',
            password: 'BBBBBB',
            country: 'BBBBBB',
            city: 'BBBBBB',
            zip: 'BBBBBB',
            street: 'BBBBBB',
            email: 'BBBBBB',
            phone: 'BBBBBB',
            language: 'BBBBBB',
            dataOfRegistration: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            birthday: currentDate,
            dataOfRegistration: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ChakaUser', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
