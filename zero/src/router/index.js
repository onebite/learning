import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'

Vue.use(Router)

import Layout from '@/views/layout/Layout'

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: () => import('@/views/login/index')
    },

    {
      path: '/hello',
      name: 'HelloWorld',
      component: HelloWorld
    }
  ]
})
