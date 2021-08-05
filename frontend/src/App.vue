<template>
  <div>
    <v-app>
      <v-app-bar
        app
        flat
        color="white"  
        light
      >
        <RouterLink :to="{ name: 'Main' }">
          <img src="@/assets/LOGO.jpg" width="40" class="pt-2">
        </RouterLink>

        <v-spacer></v-spacer>
        
        <v-text-field
          hide-details
          prepend-icon="mdi-magnify"
          single-line
          placeholder="Search"
        ></v-text-field>
      </v-app-bar>
      <!-- 메인페이지가 routerview default -->
      <v-content>
        <RouterView></RouterView>
      </v-content>
    </v-app>
    <div class="row">
      <footer
        app
        flat
        color="white"  
        light
      >
        <RouterLink :to="{ name: 'Main' }">
          <img src="@/assets/Home.png" width="40" class="pt-2">
        </RouterLink>
        <RouterLink :to="{ name: '' }">
          <img src="@/assets/Chat.png" width="30" class="pt-2">
        </RouterLink>
        <RouterLink :to="{ name: 'MyLiveList' }">
          <img src="@/assets/live.png" width="40" class="pt-2">
        </RouterLink>
        <RouterLink :to="{ name: 'MyPage' }">
          <img src="@/assets/profile.png" width="30" class="pt-2">
        </RouterLink>
      </footer>
    </div>
  </div>
</template>

<script>
// import Navbar from '@/components/Navbar.vue'
// import { OpenVidu } from 'openvidu-browser';

export default {
  name: 'App',
  // 나중에 데이터 다 들어오면 그 때 이거 참고해서 검색 기능 구현하기
  // computed: {
  //   getfilteredData() {
  //    return this.blogs.filter(blog =>
  //       blog.name.toLowerCase().includes(
  //         this.search.toLowerCase()
  //       ) ||
  //       blog.category.toLowerCase().includes(
  //         this.search.toLowerCase()
  //       )
  //     ) 
  //   }
  // },
}
</script>

<style scope>
/* header와 content 분리*/ 
/* .wrapper {
    width:100%;
    position:fixed;
    z-index:10;
    background:inherit;
}

.bottom-wrapper {
    width:100%;
    padding-top:70px;
    z-index:5;
    overflow:auto;
} */
footer{ position:fixed; 
  left:0px; 
  bottom:0px; 
  height:50px; 
  width:100%; 
  background:white; 
  color: black; }
</style>