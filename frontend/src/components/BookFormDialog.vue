<template>
  <Dialog
    header="Modifier le livre"
    v-model:visible="visible"
    :modal="true"
    :closable="true"
    @hide="closeDialog"
  >
    <form @submit.prevent="submitForm">
      <div class="field">
        <label for="title"><strong>Titre :</strong></label>
        <InputText id="title" v-model="form.title" required />
      </div>

      <div class="field">
        <label for="summary"><strong>Résumé :</strong></label>
        <InputText id="summary" v-model="form.summary" rows="3" />
      </div>

      <div class="field">
        <label for="pageCount"><strong>Pages :</strong></label>
        <InputNumber id="pageCount" v-model="form.pageCount" :min="1" />
      </div>

      <div class="field">
        <label for="publicationDate"><strong>Date de publication :</strong></label>
        <DatePicker id="publicationDate" v-model="form.publicationDate" dateFormat="yy-mm-dd" />
      </div>

      <div class="field">
        <label><strong>Auteur(s) :</strong></label>
        <div v-for="(author, index) in form.authors" :key="index" class="author-field">
          <InputText v-model="author.name" placeholder="Nom de l'auteur" />
          <Button
            type="button"
            icon="pi pi-times"
            class="p-button-danger p-button-rounded p-button-sm"
            @click="removeAuthor(index)"
          />
        </div>
        <Button type="button" label="Ajouter un auteur" icon="pi pi-plus" @click="addAuthor" />
      </div>

      <div class="field mt-4">
        <Button type="submit" label="Sauvegarder" icon="pi pi-check" />
        <Button
          type="button"
          label="Annuler"
          icon="pi pi-times"
          class="p-button-secondary"
          @click="closeDialog()"
        />
      </div>
    </form>
  </Dialog>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import Dialog from 'primevue/dialog'
import InputText from 'primevue/inputtext'
import InputNumber from 'primevue/inputnumber'
import DatePicker from 'primevue/datepicker'
import Button from 'primevue/button'
import type { Author } from '@/types/Author.ts'
import type { Book } from '@/types/Book.ts'
import { createBook, getBookById, updateBook } from '@/services/BookService'
import type { LightweightBook } from '@/types/LightweightBook.ts'

const props = defineProps<{
  modelValue: boolean
  bookId: string | null
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: boolean): void
  (e: 'update', value: LightweightBook): void
  (e: 'close'): void
}>()

const form = ref<Book>({
  id: '',
  title: '',
  summary: '',
  pageCount: 0,
  publicationDate: new Date(),
  authors: [],
})

const visible = ref(props.modelValue)
watch(
  () => props.modelValue,
  (val) => (visible.value = val),
)
watch(visible, (val) => emit('update:modelValue', val))

watch(
  () => props.bookId,
  async (bookId) => {
    if (bookId) {
      try {
        const bookToEdit = await getBookById(bookId)

        form.value = {
          ...bookToEdit,
          authors: bookToEdit.authors.map((a: Author) => ({ ...a })),
        }
      } catch (error) {
        console.error('Erreur lors de la récupération du livre', error)
        form.value = {
          id: '',
          title: '',
          summary: '',
          pageCount: 0,
          publicationDate: new Date(),
          authors: [],
        }
      }
    }
  },
  { immediate: true },
)

function addAuthor() {
  form.value.authors.push({ name: '' })
}

function removeAuthor(index: number) {
  form.value.authors.splice(index, 1)
}

async function submitForm() {
  if (!props.bookId) {
    const savedBook = await createBook(form.value)
    emit('update', savedBook)
    closeDialog()
  } else {
    const savedBook = await updateBook(form.value)
    emit('update', savedBook)
    closeDialog()
  }
}

function closeDialog() {
  form.value = {
      id: '',
      title: '',
      summary: '',
      pageCount: 0,
      publicationDate: new Date(),
      authors: [],
    }
  emit('update:modelValue', false)
  emit('close')
}
</script>

<style scoped>
.field {
  margin-bottom: 1rem;
}
.author-field {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  margin-bottom: 0.5rem;
}
</style>
