<template>
  <div class="container">
    <v-container>
      <h2 style="margin-left:40px;font-family: 'Lora', serif;">Update your LIVE</h2>
      <hr id="top-hr">
      <v-row>
        <v-col>
          <h3 style="font-family: 'Lora', serif;font-size:15px; font-weight:bold">01 Product Info</h3>
          <div style="font-family: 'IBMPlexSansKR-Regular';">
            <v-text-field v-bind="live" v-model.trim="productName" label="상품명" :counter="20" :rules="nameRules" rows="5" required="required">{{ live.prdName }} </v-text-field>
            <v-text-field v-model.trim="productNumber" label="일련 번호" rows="5" :rules="productNumberRules" required="required"></v-text-field>
            <v-select v-model="select" :items="items" :rules="[v => !!v || 'Item is required']" label="Category" required></v-select>
            
            <div>
              <input id="file-selector" ref="file" type="file" @change="handleFileUpload()">
              <v-btn @click="upload" color=primary flat>업로드</v-btn>
              <h5 :v-bind="live">{{ live }}</h5>
            </div>
          </div>
          
          <h3 style="font-family: 'Lora', serif;font-size:15px; font-weight:bold">02 Live Info</h3>
          <div style="font-family: 'IBMPlexSansKR-Regular';">
            <v-text-field v-model.trim="title" label="Live 제목" rows="5" :rules="titleRules" placeholder="Live 제목을 입력해주세요" required="required"></v-text-field>
            <v-text-field v-model.trim="liveInfo" label="Live 상세 정보 (선택)" :counter="100" rows="5" placeholder="100자 이내로 상세 방송 정보를 입력해주세요"></v-text-field>
            <v-text-field v-model.trim="startPrice" label="경매 시작가" rows="5" :rules="startPriceRules" placeholder="경매 시작가를 입력해주세요" required="required"></v-text-field>
            
            <v-menu
              v-model="menu2"
              :close-on-content-click="false"
              transition="scale-transition"
              offset-y
              min-width="auto"
            >
              <template v-slot:activator="{ on, attrs }">
                <v-text-field
                  v-model="date"
                  label="방송 예정 날짜 (1주 이내)"
                  prepend-icon="mdi-calendar"
                  readonly
                  v-bind="attrs"
                  v-on="on"
                  :rules="DateRules"
                ></v-text-field>
              </template>
              <v-date-picker
                v-model="date"
                @input="menu2 = false"
                min="2021-08-06"
                max= "2021-08-13"
              ></v-date-picker>
            </v-menu>

          <v-dialog
            ref="dialog"
            v-model="modal2"
            :return-value.sync="time"
            persistent
            width="290px"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="time"
                label="방송 예정 시간"
                prepend-icon="mdi-clock-time-four-outline"
                readonly
                v-bind="attrs"
                v-on="on"
                :rules="DateRules"
              ></v-text-field>
            </template>
            <v-time-picker
              v-if="modal2"
              v-model="time"
              full-width
            >
              <v-spacer></v-spacer>
              <v-btn
                text
                color="red"
                @click="modal2 = false"
              >
                Cancel
              </v-btn>
              <v-btn
                text
                color="blue"
                @click="$refs.dialog.save(time)"
              >
                OK
              </v-btn>
            </v-time-picker>
          </v-dialog>
          </div>
            <v-btn @click="updateLive" x-small color="white"  
          style="margin-left:120px;margin-top:20px;margin-bottom: 10px;padding:17px; font-size:17px; color:black;font-family: 'IBMPlexSansKR-Regular';">등록</v-btn>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import rest from "../../js/httpCommon.js"

export default {
  name: 'UpdateMyLiveList',
  data: vm => ({
      live: [],
      valid: true,
      productName: '',
      nameRules: [
        v => !!v || '상품 명은 필수 항목 입니다. ',
        v => (v && v.length <= 20) || '상품 명은 20자 이상 입력할 수 없습니다.',
      ],
      productNumber: '',
      productNumberRules: [
        v => !!v || '일련 번호(serial number)는 필수 항목 입니다.',
        v => /^[a-zA-Z0-9-]*$/ .test(v) || '일련 번호는 영문숫자만 입력 가능합니다.'
      ],
      select: null,
      selectedIndex: '',
      items: [
        '의류',
        '가방',
        '신발',
        '악세사리',
      ],
      title: '',
      titleRules: [
         v => !!v || 'Live 제목은 필수 항목 입니다.',
         v => (v && v.length <= 20) || 'Live 제목은 20자 이상 입력할 수 없습니다.',
      ],
      startPrice: '',
      startPriceRules: [
        v => !!v || '경매 시작가는 필수 항목 입니다.',
        v => /^[0-9]*$/ .test(v) || '금액만 입력해주세요 (20,000원 → 20000)'
      ],
      liveInfo: '',
      liveInfoRules: [
         v => (v && v.length <= 100) || '상세 정보는 100자 이상 입력할 수 없습니다.',
      ],
      DateRules: [
        v => !!v || '방송 예정일은 필수 항목 입니다.',
      ],
      date: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      modal: false,
      menu2: false,
      time: null,
      modal2: false,
      hover:false,
      // image 
      file: null,
      albumBucketName:'dabid-s3',
      bucketRegion:'ap-northeast-2',
      IdentityPoolId: 'ap-northeast-2:afe1aff1-9c00-4010-b7f0-9d205081f0dc'
  }),
  methods: {
    updateLive: function() {
      const prdId = this.live.prdId
      const liveData = {
        userId: localStorage.getItem("userId"),
        prdName: this.productName,
        prdNo: this.productNumber,
        prdPhoto: this.prdPhoto,
        prdCategory: this.items.indexOf(this.select),
        liveTitle: this.title,
        prdPriceStart: this.startPrice,
        liveDesc: this.liveInfo,
        liveDate: this.date,
        liveTime: this.time
      }
      rest.axios({
        method: 'put',
        url: `/dabid/live/${prdId}`,
        data: liveData
      })
        .then((res) => {
          // my live list로 전환 
          this.$router.push({ name: 'MyLiveList' })
        })
        .catch((err) => {
          console.log(err)
        })
    },
  },
  mounted: function () {
    if (localStorage.getItem('jwt')) {
      // 바로 정보 가져오기 
      this.live = this.$route.params.live
      // console.log(this.live)
    } else {
      console.log('오류')
    }
  }
}
</script>

<style>

</style>