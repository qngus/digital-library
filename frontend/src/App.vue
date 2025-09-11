<template>
  <div class="app">
    <h1>Librairie numerique</h1>

    <p v-if="loading">Chargement des livres...</p>

    <ErrorAlert />

    <DataTable :loading="loading" @update:loading="loading = $event" />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import DataTable from './components/DataTable.vue'
import ErrorAlert from '@/components/ErrorAlert.vue'

const loading = ref<boolean>(true)
</script>
