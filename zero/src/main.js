import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import i18n from './lang'
import './icons'
import Cookies from 'js-cookie'
import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(Element,{
  size:Cookies.get('size') || 'medium',
  i18n: (key, value) => i18n.t(key,value)
})


Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  i18n,
  render: h => h(App)
})
