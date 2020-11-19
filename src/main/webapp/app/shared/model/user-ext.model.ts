import { IUser } from '@/shared/model/user.model';
import { INews } from '@/shared/model/news.model';

export interface IUserExt {
  id?: number;
  pthone?: string;
  user?: IUser;
  news?: INews[];
}

export class UserExt implements IUserExt {
  constructor(public id?: number, public pthone?: string, public user?: IUser, public news?: INews[]) {}
}
