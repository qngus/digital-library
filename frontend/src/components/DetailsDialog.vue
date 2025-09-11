<template>
  <Dialog
    header="Détails du livre"
    v-model:visible="visible"
    :modal="true"
    :closable="true"
    @hide="$emit('close')"
  >
    <div v-if="bookWithDetails">
      <p><strong>Titre :</strong> {{ bookWithDetails.title }}</p>
      <p><strong>Résumé :</strong> {{ bookWithDetails.summary }}</p>
      <p><strong>Pages :</strong> {{ bookWithDetails.pageCount }}</p>
      <p><strong>Date de publication :</strong> {{ bookWithDetails.publicationDate }}</p>
      <div>
        <strong>Autheur(s) :</strong>
        <ul>
          <li v-for="author in bookWithDetails.authors" :key="author.name">
            {{ author.name }}
          </li>
        </ul>
      </div>
      <div class="chatbot-container">
        <ChatBot :bookTitle="bookWithDetails.title" />
      </div>
    </div>
  </Dialog>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import Dialog from 'primevue/dialog'
import type { Book } from '@/types/Book.ts'
import { getBookById } from '@/services/BookService'
import type { AxiosError } from 'axios'
import ChatBot from './ChatBot.vue'

const props = defineProps<{
  modelValue: boolean
  bookId: string | null
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: boolean): void
  (e: 'close'): void
}>()

const bookWithDetails = ref<Book | null>(null)
const error = ref<string | null>(null)

const visible = ref(props.modelValue)
watch(
  () => props.modelValue,
  (val) => (visible.value = val),
)
watch(visible, (val) => emit('update:modelValue', val))

watch(visible, async (isOpen) => {
  if (isOpen && props.bookId !== null) {
    try {
      bookWithDetails.value = await getBookById(props.bookId)
    } catch (err: unknown) {
      const axiosError = err as AxiosError
      error.value =
        'Impossible de charger les livres : ' +
        (axiosError.response?.statusText || axiosError.message)
    }
  } else {
    bookWithDetails.value = null
  }
})
</script>

<style scoped>
.details-dialog {
  max-width: 700px;
}

.chatbot-container {
  margin-top: 20px;
  border-top: 1px solid #ccc;
  padding-top: 10px;
}
</style>
