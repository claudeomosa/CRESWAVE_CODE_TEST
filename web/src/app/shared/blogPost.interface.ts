export interface BlogPost {
  id: number;
  title: string;
  content: string;
  creationDate: string | Date;
  user: {
    fullname: string;
    username: string;
  }
}
