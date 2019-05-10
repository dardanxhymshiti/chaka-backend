import { IUserAccount } from 'app/shared/model/user-account.model';

export interface IBonus {
  id?: number;
  bonusID?: string;
  userID?: string;
  coins?: number;
  userAccount?: IUserAccount;
}

export class Bonus implements IBonus {
  constructor(
    public id?: number,
    public bonusID?: string,
    public userID?: string,
    public coins?: number,
    public userAccount?: IUserAccount
  ) {}
}
