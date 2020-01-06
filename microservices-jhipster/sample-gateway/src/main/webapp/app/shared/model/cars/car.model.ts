export interface ICar {
  id?: number;
  make?: string;
  model?: string;
  year?: number;
}

export const defaultValue: Readonly<ICar> = {};
