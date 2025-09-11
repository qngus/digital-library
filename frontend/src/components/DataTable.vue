<template>
  <DataTable
    :value="books"
    :paginator="true"
    :rows="rows"
    :totalRecords="totalRecords"
    :lazy="true"
    @page="onPage"
    @filter="onFilter"
    sortMode="multiple"
    responsiveLayout="scroll"
    stripedRows
    showGridlines
    tableStyle="min-width: 50rem"
  >
    <Column
      field="title"
      header="Titre"
      sortable
      :filter="true"
      filterPlaceholder="Filtrer par titre"
    >
      <template #header>
        <div class="p-d-flex p-ai-center">
          <span>Titre</span>
          <InputText
            v-model="filters.title"
            placeholder="Recherche..."
            @input="loadBooks(0, rows)"
            class="ml-2"
          />
        </div>
      </template>
    </Column>
    <Column
      header="Auteur"
      sortable
      :filter="true"
      filterField="authors"
      filterPlaceholder="Filtrer par auteur"
    >
      <template #header>
        <div class="p-d-flex p-ai-center">
          <span>Auteur</span>
          <InputText
            v-model="filters.authors"
            placeholder="Recherche..."
            @input="loadBooks(0, rows)"
            class="ml-2"
          />
        </div>
      </template>
      <template #body="{ data }">
        {{ data.authors.map((a: Author) => a.name).join(', ') }}
      </template>
    </Column>
    <Column header="Actions">
      <template #body="slotProps">
        <Button
          label="Details"
          icon="pi pi-info-circle"
          class="book-button"
          @click="showDetails(slotProps.data)"
        />
        <Button
          label="Éditer"
          icon="pi pi-pencil"
          class="p-button-info book-button"
          @click="showEditForm(slotProps.data)"
        />
        <Button
          label="Delete"
          icon="pi pi-trash"
          severity="danger"
          class="book-button"
          @click="deleteBook(slotProps.data)"
        />
      </template>
    </Column>
  </DataTable>

  <div class="mt-4">
    <Button label="Créer" icon="pi pi-plus" class="p-button-success" @click="showEditForm(null)" />
  </div>

  <DetailsDialog
    v-model="detailsModalVisible"
    :bookId="selectedBook?.id ?? null"
    @close="onDetailsDialogClose"
  />
  <BookFormDialog
    v-model="editModalVisible"
    :bookId="bookToEdit?.id ?? null"
    @update="onEditDialogSave"
    @close="onEditDialogClose"
  />
</template>

<script setup lang="ts">
import { ref, onMounted, reactive } from 'vue'
import type { LightweightBook } from '../types/LightweightBook'
import { getBooks, deleteBookById } from '../services/BookService'
import type { AxiosError } from 'axios'
import DataTable, { type DataTableFilterEvent } from 'primevue/datatable'
import Column from 'primevue/column'
import Button from 'primevue/button'
import { InputText } from 'primevue'
import DetailsDialog from './DetailsDialog.vue'
import BookFormDialog from './BookFormDialog.vue'

import type { Author } from '@/types/Author.ts'
import type { Page } from '@/types/Page.ts'
import type { PageEvent } from '@/types/PageEvent.ts'
import type { BookQueryParams } from '@/types/BookQueryParams.ts'

const books = ref<LightweightBook[]>([])
const error = ref<string | null>(null)
const totalRecords = ref(0)
const rows = 5

type UpdateLoading = (event: 'update:loading', value: boolean) => void
const emit = defineEmits<UpdateLoading>()
const detailsModalVisible = ref(false)
const editModalVisible = ref(false)
const selectedBook = ref<LightweightBook | null>(null)
const bookToEdit = ref<LightweightBook | null>(null)

const filters = reactive<Record<string, string>>({
  title: '',
  authors: '',
})

function showDetails(book: LightweightBook) {
  selectedBook.value = book
  detailsModalVisible.value = true
}

function onDetailsDialogClose() {
  selectedBook.value = null
}

function showEditForm(book: LightweightBook | null) {
  bookToEdit.value = book
  editModalVisible.value = true
}

function onEditDialogClose() {
  bookToEdit.value = null
}

function onEditDialogSave(updatedBook: LightweightBook) {
  const index = books.value.findIndex((b) => b.id === updatedBook.id)
  if (index !== -1) {
    books.value[index] = updatedBook
  } else {
    books.value.push(updatedBook)
  }
  bookToEdit.value = null
}

function onPage(event: PageEvent) {
  loadBooks(event.page, event.rows)
}

function onFilter(event: DataTableFilterEvent) {
  for (const key in event.filters) {
    const filter = event.filters[key]
    if (typeof filter === 'object' && 'value' in filter) {
      filters[key] = filter.value ?? ''
    } else if (typeof filter === 'string') {
      filters[key] = filter
    } else {
      filters[key] = ''
    }
  }
  loadBooks(0, rows)
}

async function loadBooks(page = 0, size = rows) {
  try {
    const params: BookQueryParams = { page, size }

    if (filters.title) params.title = filters.title
    if (filters.authors) params.author = filters.authors

    const returnedPage: Page<LightweightBook> = await getBooks(params)
    books.value = returnedPage.content
    totalRecords.value = returnedPage.totalElements
  } catch (err: unknown) {
    const axiosError = err as AxiosError
    error.value =
      'Impossible de charger les livres : ' +
      (axiosError.response?.statusText || axiosError.message)
  } finally {
    emit('update:loading', false)
  }
}

async function deleteBook(book: LightweightBook) {
  try {
    await deleteBookById(book.id)
    books.value = books.value.filter((b) => b.id !== book.id)
  } catch (err: unknown) {
    const axiosError = err as AxiosError
    error.value =
      'Impossible de supprimer le livre : ' +
      (axiosError.response?.statusText || axiosError.message)
  }
}

onMounted(async () => loadBooks())
</script>
