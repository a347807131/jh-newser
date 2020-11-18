import { IUser } from '@/shared/model/user.model';

export interface IUserExt {
  id?: number;
  pthone?: string;
  user?: IUser;
}

export class UserExt implements IUserExt {
  constructor(public id?: number, public pthone?: string, public user?: IUser) {}
}
