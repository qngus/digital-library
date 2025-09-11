import type { Author } from './Author.ts'
export interface LightweightBook {
  id: string
  title: string
  authors: Author[]
}
