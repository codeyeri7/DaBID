<template>
  <div>
    <v-container>
      <h2>Live Info</h2>
      <v-row>
        <v-col>
          <!-- <label>상품명</label> -->
          <v-text-field v-model.trim="productName" label="상품명" :counter="20" :rules="nameRules" rows="5" placeholder="정확한 상품명을 입력해주세요" required="required"></v-text-field>
          <hr>
          <v-text-field v-model.trim="productNumber" label="일련 번호" rows="5" placeholder="xxx-xxxx 형식으로 입력해주세요" required="required"></v-text-field>
          <hr>
          <v-file-input label="상품 사진" filled prepend-icon="mdi-camera"></v-file-input>
          <hr>
          <v-select v-model="select" :items="items" :rules="[v => !!v || 'Item is required']" label="Item" required></v-select>
          <hr>
          <v-text-field v-model.trim="title" label="Live 제목" rows="5" placeholder="Live 제목을 입력해주세요" required="required"></v-text-field>
          <hr>
          <v-text-field v-model.trim="startPrice" label="경매 시작가" rows="5" placeholder="경매 시작가를 입력해주세요" required="required"></v-text-field>
          <hr>
          <v-text-field v-model.trim="liveInfo" label="Live 상세 정보" rows="5" placeholder="100자 이내로 상세 방송 정보를 입력해주세요" required="required"></v-text-field>
          <hr>
          <v-col cols="12" lg="6">
            <v-menu
              ref="menu1"
              v-model="menu1"
              :close-on-content-click="false"
              transition="scale-transition"
              offset-y
              max-width="290px"
              min-width="290px"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                  v-model="dateFormatted"
                  label="Date"
                  hint="MM/DD/YYYY format"
                  persistent-hint
                  v-bind="attrs"
                  @blur="date = parseDate(dateFormatted)"
                  v-on="on"
                ></v-text-field>
              </template>
              <v-date-picker v-model="date" no-title @input="menu1 = false"></v-date-picker>
            </v-menu>
          </v-col>
          <br />
          <div id="success"></div>
          <RouterLink :to="{ name: 'MyLiveList' }">
            <v-btn @click="createLive" x-small color="primary" dark>START</v-btn>
          </RouterLink>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
// import rest from "../../js/httpCommon.js"
import axios from 'axios'
export default {
  name: 'LiveInfo',
  data: vm => ({
      valid: true,
      productName: '',
      nameRules: [
        v => !!v || 'Name is required',
        v => (v && v.length <= 20) || 'Name must be less than 20 characters',
      ],
      productNumber: '',
      productNumberRules: [
        v => !!v || 'productNumber is required',
      ],
      productPhoto: '',
      select: null,
      items: [
        '의류',
        '가방',
        '신발',
        '악세사리',
      ],
      title: '',
      startPrice: '',
      liveInfo: '',
      date: new Date().toISOString().substr(0, 10),
      dateFormatted: vm.formatDate(new Date().toISOString().substr(0, 10)),
      menu1: false,
  }),
  computed: {
      computedDateFormatted () {
        return this.formatDate(this.date)
      },
    },
  watch: {
    date () {
      this.dateFormatted = this.formatDate(this.date)
    },
  },
  methods: {
    createLive() {
      const live = {
        userId: localStorage.getItem("userId"),
        prdName: this.productName,
        prdNo: this.productNumber,
        prdPhoto: this.productPhoto,
        prdCategory: 0,
        liveTitle: this.title,
        prdPriceStart: this.startPrice,
        liveDesc: this.liveInfo,
        liveDate: this.date,
      }
      if (live.liveTitle) {
        axios({
          method: 'post',
          url: 'https://localhost:8080/dabid/live/',
          data: live,
        })
          .then((res) => {
            console.log(res)
            this.$router.push({ name: 'MyLiveList' })
          })
          .catch((err) => {
            console.log(err)
          })
      }
    },
    formatDate (date) {
        if (!date) return null

        const [year, month, day] = date.split('-')
        return `${month}/${day}/${year}`
    },
    parseDate (date) {
      if (!date) return null

      const [month, day, year] = date.split('/')
      return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`
    },
}
}
</script>

<style>

</style>