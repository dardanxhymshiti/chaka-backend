import { IChakaUser } from 'app/shared/model/chaka-user.model';

export interface ITransaction {
  id?: number;
  transactionID?: string;
  userID?: string;
  description?: string;
  type?: string;
  total?: string;
  chakaUser?: IChakaUser;
}

export class Transaction implements ITransaction {
  constructor(
    public id?: number,
    public transactionID?: string,
    public userID?: string,
    public description?: string,
    public type?: string,
    public total?: string,
    public chakaUser?: IChakaUser
  ) {}
}
