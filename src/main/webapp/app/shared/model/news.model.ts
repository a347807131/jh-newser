import { IUserExt } from '@/shared/model/user-ext.model';

export interface INews {
  id?: number;
  title?: string;
  source?: string;
  link?: string;
  kind?: string;
  time?: Date;
  content?: any;
  userExts?: IUserExt[];
}

export class News implements INews {
  constructor(
    public id?: number,
    public title?: string,
    public source?: string,
    public link?: string,
    public kind?: string,
    public time?: Date,
    public content?: any,
    public userExts?: IUserExt[]
  ) {}
}
