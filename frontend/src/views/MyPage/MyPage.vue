<template>
  <div>
     <h2 style="margin-left:40px;font-family: 'Lora', serif;">{{ person.userName }} yunseo's Profile</h2>
    <MyProfile/>
      <v-card
        class="mx-auto"
        max-width="300"
      >
        <v-list>
          <v-list-item-group v-model="model">
            <v-list-item
              v-for="(item, i) in items"
              :key="i"
              @click="menuActionClick(item.action)"
            >
              <v-list-item-icon>
                <v-icon v-text="item.icon" style="color:#FF7043"></v-icon>
              </v-list-item-icon>
              <v-list-item-content>
                <v-list-item-title v-text="item.text" id="item-text"></v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-item-group>
        </v-list>
      </v-card>
  </div>
</template>

<script>
import MyProfile from "@/components/MyProfile"
import rest from "../../js/httpCommon.js"

export default {
  name: 'Mypage',
  components: {
    MyProfile,
  },
  data: function () {
    return {
      person: [],
      items: [
        {
          icon: 'mdi-monitor',
          text: 'My B-live',
          action: "goMyLive"
        },
        {
          icon: 'mdi-star',
          text: 'My Wish Live',
          action: "goWishLive"
        },
        {
          icon: 'mdi-send',
          text: 'Chat List',
          action: "goChat"
        },
        {
          icon: 'mdi-logout',
          text: 'Logout',
          action: "logout"
        },
      ],
      model: 1,
    }
  },
  methods: {
    setToken: function () {
        const jwtToken = localStorage.getItem('jwt')
        const config = {
          Authorization: `Bearer ${jwtToken}`
        }
        return config
      },
      getProfile: function() {
        rest.axios({
          method: 'get',
          url: 'dabid/users/me',
          headers: this.setToken()
        })
          .then((res) => {
            console.log(res.data)
            this.person = res.data
          })
          .catch((err) => {
            console.log(err)
          })
      },
    async logout() {
    // google 로그아웃 
      const result = await this.$gAuth.signOut()
      console.log('result', result)
      console.log('logout성공이닷')
      // localstorage 처리 
      this.isLogin = false
      localStorage.removeItem('userId')
      localStorage.removeItem('jwt')
      localStorage.removeItem('userName')
      this.$router.push({ name: 'main' })
      this.$router.go();
    },
    menuActionClick(action) {
      if (action === 'logout') {
        this.logout()
      }
      else if (action === 'goChat') {
        this.$router.push({ name: 'ChattingList' })
      }
      else if (action === 'goWishLive') {
        this.$router.push({ name: 'MyWishList' })
      }
      else if (action === 'goMyLive') {
        this.$router.push({ name: 'MyLiveList' })
      }
    }
  },
  created: function () {
    if (localStorage.getItem('jwt')) {
      this.getProfile()
    } else {
      this.$router.push({ name: 'Login' })
    }
  }
}
</script>

<style scoped>
  .btn {
    font-family: 'PT Serif'
  }
  #item-text {
    font-family: 'PT Serif', serif;
  }
</style>