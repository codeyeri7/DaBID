<template>
  <div class="container main-back">
    <div class="notice-head pb-4">
      <h1 class="ms-2" id="eng-font">Notices</h1>
        <v-btn color="primary" dark tile @click="none" id="kor-font"
        style="padding-bottom:0">
          모두 닫기
        </v-btn>
      </div>
    <v-expansion-panels
      v-model="panel"
      multiple
    >
      <v-expansion-panel
        v-for="(notice,i) in notices"
        :key="i"
        id="kor-font"
      >
        <v-expansion-panel-header>
          <div><img v-if="notice.noticeType == true" src="@/assets/exclamation-mark.png" alt="alert-img" width="20"> {{ notice.noticeTitle }} </div>
        </v-expansion-panel-header>

        <v-expansion-panel-content>
          <p v-html="notice.noticeContent"></p>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>
  </div>
</template>

<script>
import rest from "../../js/httpCommon.js"
export default {
  name: 'NoticeList',
  data () {
      return {
        panel: [],
        notices: null,
      }
    },
    methods: {
      // Reset the panel
      none () {
        this.panel = []
      },
      // 공지사항 가져오기 
      getNotices: function () {
        rest
          .axios({
            method: "get",
            url: "/dabid/notice",
            params: {
              page: 0
            }
          })
          .then((res) => {
            this.notices = res.data.content
          })
          .catch((err) => {
            console.log(err);
          });
      },
    },
    created: function () {
      this.getNotices();
  },
}
</script>

<style scoped>
.notice-head {
  display:flex; 
  justify-content:space-between;
  align-items: center;
}
</style>