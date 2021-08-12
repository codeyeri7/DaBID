import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/main.vue'
import Login from '../views/MyPage/Login.vue'
import MyPage from '../views/MyPage/MyPage.vue'
import LiveInfo from '../views/Live/LiveInfo.vue'
// import Session from '../views/Live/Session.vue'
import session from '../views/Live/Session.vue'
import MyLiveList from '../views/LiveList/MyLiveList.vue'
import UpdateMyLiveList from '../views/LiveList/UpdateMyLiveList.vue'
import MyWishList from '../views/LiveList/MyWishList.vue'
import NoticeList from '../views/Notice/NoticeList.vue'
import AllLiveList from '../views/LiveList/AllLiveList.vue'
import ReviewCreate from '../views/MyPage/ReviewCreate.vue'
import ReviewList from '../views/MyPage/ReviewList.vue'
import Chatroom from '../views/MyPage/Chatroom.vue'
import Chatlist from '../views/MyPage/Chatlist.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/Main',
    name: 'Main',
    component: Main
  },
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/MyPage',
    name: 'MyPage',
    component: MyPage
  },
  {
    path: '/LiveInfo',
    name: 'LiveInfo',
    component: LiveInfo
  },
  {
    path: '/session',
    name: 'session',
    component: session
  },
  {
    path: '/MyLiveList',
    name: 'MyLiveList',
    component: MyLiveList
  },
  {
    path: '/update',
    name: 'UpdateMyLiveList',
    component: UpdateMyLiveList
  },
  {
    path: '/notice',
    name: 'NoticeList',
    component: NoticeList
  },
  {
    path: '/AllLiveList',
    name: 'AllLiveList',
    component: AllLiveList
  },
  {
    path: '/ReviewCreate',
    name: 'ReviewCreate',
    component: ReviewCreate
  },
  {
    path: '/MyWishList',
    name: 'MyWishList',
    component: MyWishList
  },
  {
    path: '/Chatroom',
    name: 'Chatroom',
    component: Chatroom
  },
  {
    path: '/Chatlist',
    name: 'Chatlist',
    component: Chatlist
  },
  {
    path: '/ReviewList',
    name: 'ReviewList',
    component: ReviewList
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
export default router

