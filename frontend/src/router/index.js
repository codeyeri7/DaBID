import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/main.vue'
import Login from '../views/MyPage/Login.vue'
import MyPage from '../views/MyPage/MyPage.vue'
import LiveInfo from '../views/Live/LiveInfo.vue'
import session from '../views/Live/Session.vue'
import Seller from '../views/Live/Seller.vue'
import MyLiveList from '../views/LiveList/MyLiveList.vue'
import UpdateMyLiveList from '../views/LiveList/UpdateMyLiveList.vue'
import MyWishList from '../views/LiveList/MyWishList.vue'
import NoticeList from '../views/Notice/NoticeList.vue'
import AllLiveList from '../views/LiveList/AllLiveList.vue'
import ReviewCreate from '../views/MyPage/ReviewCreate.vue'
import ReviewList from '../views/MyPage/ReviewList.vue'
import Chatroom from '../views/MyPage/Chatroom.vue'
import Chatlist from '../views/MyPage/Chatlist.vue'
import TheCheat from '../views/MyPage/TheCheat.vue'
import Notice from '../views/Notice/NoticeList.vue'
import UserProfile from '../views/MyPage/UserProfile.vue'


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
    path: '/Seller',
    name: 'Seller',
    component: Seller
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
    path: '/ReviewList',
    name: 'ReviewList',
    component: ReviewList
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
    path: '/TheCheat',
    name: 'TheCheat',
    component: TheCheat
  },
  {
    path: '/Notice',
    name: 'Notice',
    component: Notice
  },
  {
    path: '/UserProfile',
    name: 'UserProfile',
    component: UserProfile
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
  // 여기 빨간 줄 무시하셔도 됩니당..!
  scrollBehavior (to, from, savedPosition) {
    return { x: 0, y: 0 }
  }
})
export default router

