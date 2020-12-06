/*
 * @Descripttion    : 路由
 * @Author          : RyzeYang
 * @version         : 1.0
 * @Date            : 2020-11-03 22:15:46
 * @LastEditors     : RyzeYang
 * @LastEditTime    : 2020-11-05 06:45:10
 */
import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

/**
 * 有这个角色的话就显示某个功能，
 */
export const constantRoutes = [
  {
    path: '/login',
    name: 'login',
    component: () => import('@/views/Login')
  }
  /* , {
    path: '/',
    name: 'Dashboard',
    meta: { roles: ['admin', 'user'] },
    component: () => import('@/views/Dashboard')
  } */
]

export const asyncRoutes = [
  {
    path: '/',
    name: 'Dashboard',
    component: () => import('@/views/Dashboard'),
    children: [
      {
        path: '/a',
        name: 'a',
        meta: { roles: ['admin'] },
        component: () => import('@/views/A')
      },
      {
        path: '/t',
        name: 'table',
        meta: { roles: ['admin', 'user'] },
        component: () => import('@/views/Table')
      }
    ]
  }
]

const createRouter = () => new VueRouter({
  mode: 'history',
  routes: constantRoutes
})

const router = createRouter()

console.log(router)

export function resetRouter () {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
