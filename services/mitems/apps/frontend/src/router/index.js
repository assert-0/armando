import Vue from 'vue'
import VueRouter from 'vue-router'
import Flows from '../views/Flows.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Flows',
    component: Flows
  },
  {
    path: '/import-data',
    name: 'Import Mitems Data',
    component: () => import(/* webpackChunkName: "flow-form" */ '../views/ImportData')
  },
  {
    path: '/flows/add',
    name: 'Adding Flow',
    component: () => import(/* webpackChunkName: "flow-form" */ '../views/FlowForm.vue')
  },
  {
    path: '/flows/:id',
    name: 'Flow',
    component: () => import(/* webpackChunkName: "flow" */ '../views/Flow.vue')
  },
  {
    path: '/flows/:id/edit',
    name: 'Editing Flow',
    component: () => import(/* webpackChunkName: "flow-form" */ '../views/FlowForm.vue')
  },
  {
    path: '/flows/:flowId/items/add',
    name: 'Adding Item',
    component: () => import(/* webpackChunkName: "item" */ '../views/Item.vue')
  },
  {
    path: '/flows/:flowId/items/:id',
    name: 'Editing Item',
    component: () => import(/* webpackChunkName: "item" */ '../views/Item.vue')
  },
  {
    path: '/remove/:what/:id',
    name: 'Removing Object',
    component: () => import(/* webpackChunkName: "item" */ '../views/Remove.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
