import type { Author } from './Author.ts'
export interface Book {
  id: string
  title: string
  authors: Author[]
  publicationDate: Date
  summary: string
  pageCount: number
}
