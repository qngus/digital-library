import { reactive } from 'vue'
import type { Error } from '@/types/Error.ts'

export const errorState = reactive<{ error: Error | null }>({ error: null })
