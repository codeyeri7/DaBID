import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/main.vue'
import Login from '../views/MyPage/Login.vue'
import MyPage from '../views/MyPage/MyPage.vue'
import ProductInfo from '../views/Live/ProductInfo.vue'
import LiveInfo from '../views/Live/LiveInfo.vue'
import Session from '../views/Live/Session.vue'
import Seller from '../views/Live/Seller.vue'
import MyLiveList from '../views/LiveList/MyLiveList.vue'

// import store from '../store'

// const beforeAuth = isAuth => (from, to, next) => {
//   const isAuthenticated = store.getters["isAuthenticated"]
//   if ((isAuthenticated && isAuth) || (!isAuthenticated && !isAuth)) {
//     return next()
//   } else {
//     // 홈 화면으로 이동
//     next("/")
//   }
// }

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

