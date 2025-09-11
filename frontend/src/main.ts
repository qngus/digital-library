import './assets/main.css'

import { createApp, Plugin } from 'vue'
import App from './App.vue'
import PrimeVue from 'primevue/config'
import Aura from '@primeuix/themes/aura'
import 'primeicons/primeicons.css'

const app = createApp(App)

app.use(PrimeVue as unknown as Plugin, {
  theme: {
    preset: Aura,
  },
})

app.mount('#app')
