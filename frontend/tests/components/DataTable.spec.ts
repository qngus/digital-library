import { mount, flushPromises } from '@vue/test-utils'
import { ComponentPublicInstance, nextTick, Plugin } from 'vue'
import { describe, it, expect, vi, beforeEach } from 'vitest'
import DataTableComponent from '@/components/DataTable.vue'
import BookFormDialog from '@/components/BookFormDialog.vue'

import PrimeVue from 'primevue/config'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Button from 'primevue/button'
import InputText from 'primevue/inputtext'
import Dialog from 'primevue/dialog'
import InputNumber from 'primevue/inputnumber'
import DatePicker from 'primevue/datepicker'
import { deleteBookById, getBooks } from '@/services/BookService.js'
import { Book } from '@/types/Book.js'


vi.mock('@/services/BookService')

describe('DataTable.vue', () => {
  const mockBook = {
    id: '1',
    title: 'Clean Code',
    summary: 'A great book',
    pageCount: 400,
    publicationDate: '2008-08-01',
    authors: [{ name: 'Robert C. Martin' }],
  }

  const mockPage = {
    content: [mockBook],
    totalElements: 1,
    totalPages: 1,
    size: 5,
    number: 0,
  }

  beforeEach(() => {
    vi.resetAllMocks()

    const mockedGetBooks = vi.mocked(getBooks)
    const mockedDeleteBookById = vi.mocked(deleteBookById)

    mockedGetBooks.mockResolvedValue(mockPage)
    mockedDeleteBookById.mockResolvedValue(undefined)

    Object.defineProperty(window, 'matchMedia', {
      writable: true,
      value: (query: string) => ({
        matches: false,
        media: query,
        onchange: null,
        addListener: vi.fn(),
        removeListener: vi.fn(),
        addEventListener: vi.fn(),
        removeEventListener: vi.fn(),
        dispatchEvent: vi.fn(),
      }),
    })
  })

  function mountDataTable() {
    return mount(DataTableComponent, {
      global: {
        plugins: [PrimeVue as unknown as Plugin],
        components: {
          DataTable,
          Column,
          Button,
          InputText,
          BookFormDialog,
          Dialog,
          InputNumber,
          DatePicker,
        },
      },
    })
  }

  it('charge et affiche les livres au montage', async () => {
    const wrapper = mountDataTable()
    await flushPromises()

    expect(getBooks).toHaveBeenCalled()
    const rows = wrapper.findAll('tr')
    expect(rows.length ).toBe(1)
    expect(wrapper.text()).toContain('Clean Code')
    expect(wrapper.text()).toContain('Robert C. Martin')
  })

  it('ouvre BookFormDialog quand on clique sur "Éditer"', async () => {
    const wrapper = mountDataTable()
    await flushPromises()

    const editButton = wrapper.find('button.p-button-info')
    await editButton.trigger('click')
    await flushPromises()
    await nextTick()

    const dialog = wrapper.findComponent(BookFormDialog)
    expect(dialog.exists()).toBe(true)
    expect(dialog.props('bookId')).toBe(mockBook.id)
  })

  it('supprime un livre quand on clique sur "Delete"', async () => {
    const wrapper = mountDataTable()
    const vm = wrapper.vm as ComponentPublicInstance<{ books: Book[] }>
    await flushPromises()

    const deleteButton = wrapper.find('button.p-button-danger')
    await deleteButton.trigger('click')
    await flushPromises()

    expect(deleteBookById).toHaveBeenCalledWith(mockBook.id)
    expect(vm.books.length).toBe(0)
  })
})
