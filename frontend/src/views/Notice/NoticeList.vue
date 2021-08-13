<template>
  <div>
    <h1>Notices</h1>
    <div class="text-center d-flex pb-4">
      <v-btn @click="all">
        All Notices
      </v-btn>
      <v-btn @click="none">
        All close
      </v-btn>
    </div>

    <v-expansion-panels
      v-model="panel"
      multiple
    >
      <v-expansion-panel
        v-for="(notice,i) in notices"
        :key="i"
      >
        <v-expansion-panel-header>
          #{{ i }} {{ notice.title }}
          <!-- 조회수가 DB에 저장 되나요? 스토리보드에 있길래용 --> 

        </v-expansion-panel-header>
        <v-expansion-panel-content>
          {{ notice.content }}
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
      // Create an array the length of our items
      // with all values as true
      all () {
        this.panel = [...Array(this.items).keys()].map((k, i) => i)
      },
      // Reset the panel
      none () {
        this.panel = []
      },
    },
    // 공지사항 가져오기 
    getNotices: function () {
      rest
        .axios({
          method: "get",
          url: "",
        })
        .then((res) => {
          this.notices = res.data;
        })
        .catch((err) => {
          console.log(err);
        });
    },
    created: function () {
      this.getNotices();
  },
}
</script>

<style scoped>
h1 {
  font-family: 'PT Serif', serif;
}

</style>