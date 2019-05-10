import { Moment } from 'moment';
import { IUserAccount } from 'app/shared/model/user-account.model';
import { ITransaction } from 'app/shared/model/transaction.model';
import { IInvitation } from 'app/shared/model/invitation.model';

export interface IChakaUser {
  id?: number;
  userID?: string;
  name?: string;
  surname?: string;
  birthday?: Moment;
  username?: string;
  password?: string;
  country?: string;
  city?: string;
  zip?: string;
  street?: string;
  email?: string;
  phone?: string;
  language?: string;
  dataOfRegistration?: Moment;
  userID?: IUserAccount;
  userIDS?: ITransaction[];
  userIDS?: IInvitation[];
}

export class ChakaUser implements IChakaUser {
  constructor(
    public id?: number,
    public userID?: string,
    public name?: string,
    public surname?: string,
    public birthday?: Moment,
    public username?: string,
    public password?: string,
    public country?: string,
    public city?: string,
    public zip?: string,
    public street?: string,
    public email?: string,
    public phone?: string,
    public language?: string,
    public dataOfRegistration?: Moment,
    public userID?: IUserAccount,
    public userIDS?: ITransaction[],
    public userIDS?: IInvitation[]
  ) {}
}
