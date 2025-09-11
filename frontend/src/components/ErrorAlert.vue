<template>
  <transition name="fade">
    <div v-if="errorState.error" class="error-alert">
      <p v-for="(msg, field) in errorState.error" :key="field">{{ field }}: {{ msg }}</p>
      <button class="close-btn" @click="close">&times;</button>
    </div>
  </transition>
</template>

<script setup lang="ts">
import { errorState } from '@/state/ErrorState'
import { watch } from 'vue'

const close = () => {
  errorState.error = null
}

watch(
  () => errorState.error,
  (newError) => {
    if (newError) {
      setTimeout(() => {
        close()
      }, 5000)
    }
  },
)
</script>

<style scoped>
.error-alert {
  position: fixed;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 9999;
  background-color: #fee2e2;
  border: 1px solid #fca5a5;
  color: #b91c1c;
  padding: 16px 24px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  max-width: 400px;
  text-align: center;
}

.close-btn {
  position: absolute;
  top: 4px;
  right: 8px;
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
  color: #b91c1c;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
