import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/main.vue'
import Login from '../views/MyPage/Login.vue'
import MyPage from '../views/MyPage/MyPage.vue'
import ProductInfo from '../views/Live/ProductInfo.vue'
import LiveInfo from '../views/Live/LiveInfo.vue'
// import Dashboard from '../views/Live/Dashboard.vue'
import Session from '../views/Live/Session.vue'
import Seller from '../views/Live/Seller.vue'
import MyLiveList from '../views/LiveList/MyLiveList.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Main',
    component: Main
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/MyPage',
    name: 'MyPage',
    component: MyPage
  },
  {
    path: '/ProductInfo',
    name: 'ProductInfo',
    component: ProductInfo
  },
  {
    path: '/LiveInfo',
    name: 'LiveInfo',
    component: LiveInfo
  },
  // {
  //   path: '/dashboard',
  //   name: 'dashboard',
  //   component: Dashboard
  // },
  {
    path: '/session',
    name: 'Session',
    component: Session
  },
  {
    path: '/Seller',
    name: 'Seller',
    component: Seller
  },
  {
    path: '/MyLiveList',
    name: 'MyLiveList',
    component: MyLiveList
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
export default router

