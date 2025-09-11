import type { Book } from '@/types/Book.ts'
import type { BookQueryParams } from '@/types/BookQueryParams.ts'
import type { LightweightBook } from '@/types/LightweightBook.ts'
import type { Page } from '@/types/Page.ts'
import type { Error } from '@/types/Error.ts'
import type { AxiosError, AxiosResponse } from 'axios'
import api from './AxiosService.js'
import { errorState } from '@/state/ErrorState.js'

async function handleRequest<T>(request: Promise<T>): Promise<T> {
  try {
    return await request
  } catch (error) {
    const axiosError = error as AxiosError<Error>
    let errorData: Error
    if (axiosError.response && axiosError.response.data) {
      errorData = axiosError.response.data
    } else {
      errorData = {
        httpStatusValue: 0,
        errorCause: 'Network Error',
        message: 'Impossible de contacter le serveur',
      }
    }
    errorState.error = errorData
    throw errorData
  }
}

export async function getBooks(params: BookQueryParams): Promise<Page<LightweightBook>> {
  return handleRequest(api.get<Page<LightweightBook>>('/api/books', { params }).then((r: AxiosResponse) => r.data))
}

export async function getBookById(bookId: string): Promise<Book> {
  return handleRequest(api.get<Book>('/api/books/' + bookId).then((r: AxiosResponse) => r.data))
}

export async function deleteBookById(bookId: string): Promise<void> {
  return handleRequest(api.delete('/api/books/' + bookId).then((r: AxiosResponse) => r.data))
}

export async function createBook(book: Book): Promise<LightweightBook> {
  return handleRequest(api.post('/api/books', book).then((r: AxiosResponse) => r.data))
}

export async function updateBook(book: Book): Promise<LightweightBook> {
  return handleRequest(api.put('/api/books', book).then((r: AxiosResponse) => r.data))
}
