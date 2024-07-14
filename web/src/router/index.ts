import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'index',
      component: () => import('../views/Index.vue')
    },
    {
      path: '/admin',
      name: 'admin',
      component: () => import('../views/admin/AdminIndex.vue')
    },
    {
      path: '/admin/login',
      name: 'adminlogin',
      component: () => import('../views/admin/AdminLogin.vue')
    },
    {
      path: '/user',
      name: 'user',
      component: () => import('../views/user/UserIndex.vue')
    }
  ]
})

export default router
