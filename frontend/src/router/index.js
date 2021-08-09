import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/main.vue'
import Login from '../views/MyPage/Login.vue'
import MyPage from '../views/MyPage/MyPage.vue'
import LiveInfo from '../views/Live/LiveInfo.vue'
import Session from '../views/Live/Session.vue'
import MyLiveList from '../views/LiveList/MyLiveList.vue'
// import MyLiveListEnd from '../views/LiveList/MyLiveListEnd.vue'
import UpdateMyLiveList from '../views/LiveList/UpdateMyLiveList.vue'
// import OnetoOneChat from '../views/MyPage/OnetoOneChat.vue'
// import OnetoOneChatList from '../views/MyPage/OnetoOneChatList.vue'
import ReviewCreate from '../views/MyPage/ReviewCreate.vue'
import Chat from '../views/MyPage/Chat.vue'

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
    name: 'Session',
    component: Session
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
  // {
  //   path: '/onetoonechat',
  //   name: 'OnetoOneChat',
  //   component: OnetoOneChat
  // },
  // {
  //   path: '/onetoonechatlist',
  //   name: 'OnetoOneChatList',
  //   component: OnetoOneChatList
  // },
  {
    path: '/ReviewCreate',
    name: 'ReviewCreate',
    component: ReviewCreate
  },
  {
    path: '/Chat',
    name: 'Chat',
    component: Chat
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
export default router

