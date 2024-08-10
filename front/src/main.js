import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import axios from "axios";

const app = createApp(App)
app.use(router)
app.use(ElementPlus)
for (const [key, value] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, value)
}
app.mount('#app')

axios.defaults.baseURL = 'http://localhost:8080'
