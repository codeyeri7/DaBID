import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/main.vue'
import Login from '../views/MyPage/Login.vue'
import MyPage from '../views/MyPage/MyPage.vue'
import LiveInfo from '../views/Live/LiveInfo.vue'
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
import UserProfile from '../views/MyPage/UserProfile.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/Main',
    name: 'Main',
    component: Main,
    meta: {
      title: "Main",
    },
  },
  {
    path: '/',
    name: 'Login',
    component: Login,
    meta: {
      title: "",
    },
  },
  {
    path: '/MyPage',
    name: 'MyPage',
    component: MyPage,
    meta: {
      title: "MyPage",
    },
  },
  {
    path: '/LiveInfo',
    name: 'LiveInfo',
    component: LiveInfo,
    meta: {
      title: "LiveInfo",
    },
  },
  {
    path: '/session',
    name: 'session',
    component: session
  },
  {
    path: '/MyLiveList',
    name: 'MyLiveList',
    component: MyLiveList,
    meta: {
      title: "MyLive",
    },
  },
  {
    path: '/update',
    name: 'UpdateMyLiveList',
    component: UpdateMyLiveList,
    meta: {
      title: "UpdateMyLive",
    },
  },
  {
    path: '/NoticeList',
    name: 'NoticeList',
    component: NoticeList,
    meta: {
      title: "Notice",
    },
  },
  {
    path: '/AllLiveList',
    name: 'AllLiveList',
    component: AllLiveList,
    meta: {
      title: "AllLive",
    },
  },
  {
    path: '/ReviewCreate',
    name: 'ReviewCreate',
    component: ReviewCreate,
    meta: {
      title: "Review",
    },
  },
  {
    path: '/ReviewList',
    name: 'ReviewList',
    component: ReviewList,
    meta: {
      title: "Review",
    },
  },
  {
    path: '/MyWishList',
    name: 'MyWishList',
    component: MyWishList,
    meta: {
      title: "MyWish",
    },
  },
  {
    path: '/Chatroom',
    name: 'Chatroom',
    component: Chatroom,
    meta: {
      title: "Chat",
    },
  },
  {
    path: '/Chatlist',
    name: 'Chatlist',
    component: Chatlist,
    meta: {
      title: "Chat",
    },
  },
  {
    path: '/UserProfile',
    name: 'UserProfile',
    component: UserProfile,
    meta: {
      title: "UserProfile",
    },
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
  // 여기 빨간 줄 무시하셔도 됩니당..!
  scrollBehavior (to, from, savedPosition) {
    return { x: 0, y: 0 }
  },
})

const makeTitle = (title) =>
  title ? `${title} | Dabid` : "Dabid";

router.afterEach((to, from) => {
  Vue.nextTick(() => {
    document.title = makeTitle(to.meta.title);
  })
})
export default router

