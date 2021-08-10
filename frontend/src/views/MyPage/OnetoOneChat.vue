<template>
  <div id="app">
    <v-app app>
      <v-app-bar color="blue" app>
        <v-app-bar-nav-icon>
        <v-icon color="white">mdi-arrow-left</v-icon>    
        </v-app-bar-nav-icon>
        <v-toolbar-title class="white--text"
          >Sushant </v-toolbar-title>
      </v-app-bar>
      <v-container class="fill-height">
        <v-row class="fill-height pb-14" align="end">
          <v-col>
            <div v-for="(item, index) in chat" :key="index" 
                :class="['d-flex flex-row align-center my-2', item.from == 'user' ? 'justify-end': null]">
              <span v-if="item.from == 'user'" class="blue--text mr-3">{{ item.msg }}</span>
              <v-avatar :color="item.from == 'user' ? 'indigo': 'red'" size="36">
                <span class="white--text">{{ item.from[0] }}</span>
              </v-avatar>
              <span v-if="item.from != 'user'" class="blue--text ml-3">{{ item.msg }}</span>
            </div>
          </v-col>
        </v-row>
      </v-container>
      <v-footer fixed>
        <v-container class="ma-0 pa-0">
          <v-row no-gutters>
            <v-col>
              <div class="d-flex flex-row align-center">
                <v-text-field v-model="msg" placeholder="Type Something" @keypress.enter="send"></v-text-field>
                <v-btn icon class="ml-4" @click="send"><v-icon>mdi-send</v-icon></v-btn>
              </div>
            </v-col>
          </v-row>
        </v-container>
      </v-footer>
    </v-app>
  </div>
</template>

<script>


export default {
  name: 'OnetoOneChat',
  data() {
    return {
      chat: [],
      msg: null,
    };
  },
  
  methods: {
    send: function(){
      this.chat.push(
      {
        from: "user",
        msg: this.msg
      })
      this.msg = null
      this.addReply()
    },
    addReply(){
      this.chat.push({
        from: "sushant",
        msg: "Hmm"
      })
    }
  }
};
</script>

<style>
</style>