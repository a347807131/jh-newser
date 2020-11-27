export interface ITops {
  id?: number;
  title?: string;
  link?: string;
  source?: string;
  time?: Date;
  seq?: number;
  note?: string;
}

export class Tops implements ITops {
  constructor(
    public id?: number,
    public title?: string,
    public link?: string,
    public source?: string,
    public time?: Date,
    public seq?: number,
    public note?: string
  ) {}
}
