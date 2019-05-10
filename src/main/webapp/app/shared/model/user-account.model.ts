import { IBonus } from 'app/shared/model/bonus.model';

export interface IUserAccount {
  id?: number;
  accountID?: string;
  userID?: string;
  bilane?: number;
  status?: string;
  userIDS?: IBonus[];
}

export class UserAccount implements IUserAccount {
  constructor(
    public id?: number,
    public accountID?: string,
    public userID?: string,
    public bilane?: number,
    public status?: string,
    public userIDS?: IBonus[]
  ) {}
}
