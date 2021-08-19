<template>
  <div class="container">
    <v-form
      ref="form"
      v-model="valid"
      lazy-validation>
      <v-row>
        <v-col>
          <h3 style="font-size:17px; font-weight:bold" id="eng-font">01 Product Info</h3>
          <div id="kor-font">
            <v-text-field v-model.trim="productName" label="상품명" :counter="20" :rules="nameRules" rows="5" placeholder="정확한 상품명을 입력해주세요" required="required"></v-text-field>
            <v-text-field v-model.trim="productNumber" label="일련 번호" rows="5" :rules="productNumberRules" placeholder="xxxx-xxxx 형식으로 입력해주세요" required="required"></v-text-field>
            <v-select v-model="select" :items="items" :rules="[v => !!v || 'Item is required']" label="Category" required></v-select>
            <h5 id="kor-font" style="color:gray">
              사진 업로드
            </h5>
              <input id="file-selector" ref="file" type="file" @change="handleFileUpload()">
              
          </div>
          <hr id="top-hr">
          <h3 style="font-size:17px; font-weight:bold" id="eng-font">02 Live Info</h3>
          <div id="kor-font">
            <v-text-field v-model.trim="title" label="Live 제목" rows="5" :rules="titleRules" placeholder="Live 제목을 입력해주세요" required="required"></v-text-field>
            <v-text-field v-model.trim="liveInfo" label="Live 상세 정보 (선택)" :counter="100" rows="5" placeholder="100자 이내로 상세 방송 정보를 입력해주세요"></v-text-field>
            <v-text-field v-model.trim="startPrice" label="경매 시작가" rows="5" :rules="startPriceRules" placeholder="경매 시작가를 입력해주세요" required="required"></v-text-field>
            
            <v-dialog
              ref="dialog"
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
                :min= "today"
                :max= "sevenday"
              ></v-date-picker>
            </v-dialog>
          <v-dialog
            ref="dialog2"
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
                @click="$refs.dialog2.save(time)"
              >
                  OK
                </v-btn>
              </v-time-picker>
            </v-dialog>
          </div>
          <v-row
            align="center"
            justify="space-around"
            class="mb-4"
          >
            <v-btn
              tile
              @click="goBack"
              class="mt-5"
            >
              <v-icon left>
                mdi-cancel
              </v-icon>
                Cancel
            </v-btn>
            <v-btn
              tile
              color="primary"
              @click="createLive"
              :disabled="!valid"
              class="mt-5"
            >
              <v-icon left>
                mdi-pencil
              </v-icon>
                Make Live
            </v-btn>
          </v-row>
        </v-col>
      </v-row>
    </v-form>
  </div>
</template>

<script>

import rest from "../../js/httpCommon.js"
import AWS from 'aws-sdk'
import dayjs from 'dayjs'

export default {
  name: 'LiveInfo',
  component: {
    dayjs
  },
  data() {
    return {
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
      // date: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      // date: dayjs().format('YYYY-MM-DD HH:mm'),
      date: '',
      today: dayjs().format('YYYY-MM-DD'),
      sevenday: '',
      modal: false,
      menu2: false,
      time: '',
      modal2: false,
      hover:false,
      // image 
      file: null,
      albumBucketName:'dabid-s3',
      bucketRegion:'ap-northeast-2',
      IdentityPoolId: 'ap-northeast-2:afe1aff1-9c00-4010-b7f0-9d205081f0dc'
    }
  },
  mounted() {
    this.calcDate()
  },
  methods: {
    setToken: function () {
      const jwtToken = localStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${jwtToken}`
      }
      return config
    },
    goBack () {
      this.$router.push({ name: 'Main' })
    },
    createLive() {
      // 유효성 검사 후 버튼 활성화
      this.$refs.form.validate()
      
      const live = {
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
      rest.
        axios({
          method: 'post',
          url: '/dabid/live/',
          data: live,
          headers: this.setToken()
        })
        .then((res) => {
          console.log(this.date)
          console.log(this.time)
          console.log(this.$refs.form)
          console.log(res)
          this.$router.push({ name: 'MyLiveList' })
        })
        .catch((err) => {
          console.log(err)
        })
    },
    setDate() {
      this.date = this.date +" "+ this.time
      this.$refs.dialog.save(this.date)
      this.$refs.dialog2.save(this.time)
    },
    handleFileUpload() {
      this.file = this.$refs.file.files[0]
      // console.log(this.file, '파일이 잘 업로드 되었습니다.')
      this.upload()
    },
    calcDate() {
      this.sevenday = dayjs(this.today).add(7, 'day').format('YYYY-MM-DD')
    },
    upload() {
      AWS.config.update({
        region: this.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
          IdentityPoolId: this.IdentityPoolId,
        })
      })

      const s3 = new AWS.S3({
        apiVersion: '2012-10-17',
        params: {
          Bucket: this.albumBucketName
        }
      })
      let photoKey = this.file.name
      s3.upload({
        Key: photoKey,
        Body: this.file,
        ACL: 'public-read'
      }, (err, data) => {
        if (err) {
          console.log(err)
          return alert('There was an error uploading your photo: ', err.message);
        }
        // alert('사진 업로드에 성공했습니다');
        this.prdPhoto = data.Location
        // console.log(this.prdPhoto)
      });
    }
  },
}
</script>

<style scoped>
.container{
  margin-bottom: 50px;
}
#top-hr{
  margin-top: 1rem;
  height:1px;
  background:rgb(0, 0, 0);
  background-image: -webkit-linear-gradient(left, #eee, rgb(19, 18, 18), #eee);
}

#check-img:hover{
  color:red
}
.container {
  width: 90%;
}
</style>