export type FormError<T> = Partial<Record<keyof T, string>> & {
  serverErrors?: string[];
};
