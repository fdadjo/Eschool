export interface Course {
  id: number;
  title: string;
  description: string;
  tagKey: string;
  categoryId: number;
  categoryName: string;
  activate: boolean;
}
