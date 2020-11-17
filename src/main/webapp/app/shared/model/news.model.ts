export interface INews {
  id?: number;
  title?: string;
  source?: string;
  link?: string;
  kind?: string;
  time?: Date;
  content?: string;
}

export class News implements INews {
  constructor(
    public id?: number,
    public title?: string,
    public source?: string,
    public link?: string,
    public kind?: string,
    public time?: Date,
    public content?: string
  ) {}
}
