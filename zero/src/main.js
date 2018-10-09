// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import i18n from './lang'
import Element from 'element-ui'

Vue.config.productionTip = false

Vue.use(Element,{
  size:Cookies.get('size') || 'medium',
  i18n: (key, value) => i18n.t(key,value)
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  i18n,
  render: h => h(App)
})
