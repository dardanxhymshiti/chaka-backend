import { IChakaUser } from 'app/shared/model/chaka-user.model';

export interface IInvitation {
  id?: number;
  invitationID?: string;
  userID?: string;
  email?: string;
  status?: string;
  chakaUser?: IChakaUser;
}

export class Invitation implements IInvitation {
  constructor(
    public id?: number,
    public invitationID?: string,
    public userID?: string,
    public email?: string,
    public status?: string,
    public chakaUser?: IChakaUser
  ) {}
}
