<template>
  <div class="container">
    <v-container>
      <h2 style="margin-left:40px;font-family: 'Lora', serif;">Make new LIVE</h2>
      <hr id="top-hr">
      <v-row>
        <v-col>
          <h3 style="font-family: 'Lora', serif;font-size:15px; font-weight:bold">01 Product Info</h3>
          <div style="font-family: 'IBMPlexSansKR-Regular';">
            <v-text-field v-model.trim="editlive.prdName" label="상품명" :counter="20" :rules="nameRules" rows="5" placeholder="정확한 상품명을 입력해주세요" required="required"></v-text-field>
            <v-text-field v-model.trim="editlive.prdNo" label="일련 번호" rows="5" :rules="productNumberRules" placeholder="xxxx-xxxx 형식으로 입력해주세요" required="required"></v-text-field>
            <v-select v-model="select" :items="items" :rules="[v => !!v || 'Item is required']" label="Category" required></v-select>
            <!-- <v-file-input id="file-selector" v-model="productPhoto" @change="handleFileUpload()"  label="상품 사진" filled prepend-icon="mdi-camera" style="margin-top:17px;"></v-file-input> -->
            <div>
              <input id="file-selector" ref="file" type="file" @change="handleFileUpload()">
            </div>
          </div>
          
          <h3 style="font-family: 'Lora', serif;font-size:15px; font-weight:bold">02 Live Info</h3>
          <div style="font-family: 'IBMPlexSansKR-Regular';">
            <v-text-field v-model.trim="editlive.liveTitle" label="Live 제목" rows="5" :rules="titleRules" placeholder="Live 제목을 입력해주세요" required="required"></v-text-field>
            <v-text-field v-model.trim="editlive.liveDesc" label="Live 상세 정보 (선택)" :counter="100" rows="5" placeholder="100자 이내로 상세 방송 정보를 입력해주세요"></v-text-field>
            <v-text-field v-model.trim="editlive.prdPriceStart" label="경매 시작가" rows="5" :rules="startPriceRules" placeholder="경매 시작가를 입력해주세요" required="required"></v-text-field>
            
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
                  v-model.trim="date"
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
                v-model.trim="time"
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
            <v-btn class="white--text" elevation="0" @click="updateLive(editlive)" x-small color="primary" :disabled="!valid"
          style="margin-left:120px;margin-top:20px;margin-bottom: 10px;padding:17px; font-size:17px; color:black;font-family: 'IBMPlexSansKR-Regular';">등록</v-btn>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import rest from "../../js/httpCommon.js"
import AWS from 'aws-sdk'
import dayjs from 'dayjs'

export default {
  name: 'UpdateMyLiveList',
  component: {
    dayjs
  },
  data: function () {
    return {
      editlive: [],
      valid: true,
      nameRules: [
        v => !!v || '상품 명은 필수 항목 입니다. ',
        v => (v && v.length <= 20) || '상품 명은 20자 이상 입력할 수 없습니다.',
      ],
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
      titleRules: [
         v => !!v || 'Live 제목은 필수 항목 입니다.',
         v => (v && v.length <= 20) || 'Live 제목은 20자 이상 입력할 수 없습니다.',
      ],
      startPriceRules: [
        v => !!v || '경매 시작가는 필수 항목 입니다.',
        v => /^[0-9]*$/ .test(v) || '금액만 입력해주세요 (20,000원 → 20000)'
      ],
      liveInfoRules: [
         v => (v && v.length <= 100) || '상세 정보는 100자 이상 입력할 수 없습니다.',
      ],
      DateRules: [
        v => !!v || '방송 예정일은 필수 항목 입니다.',
      ],
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
      IdentityPoolId: 'ap-northeast-2:afe1aff1-9c00-4010-b7f0-9d205081f0dc',
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
    getLive: function () {
      const prdId = this.$route.params.prdId
      rest.axios({
        method: 'get',
        url: `/dabid/live/${prdId}`,
        headers: this.setToken()
      })
        .then((res) => {
          this.editlive = res.data
          this.date = this.editlive.liveDate.substr(0, 10)
          this.time = this.editlive.liveDate.substr(11, 5)
        })
        .catch((err) => {
          console.log(err)
        })
    },
    updateLive: function (editlive) {
      // console.log(this.)
      const config = this.setToken()
      const prdId = this.$route.params.prdId
      const editlivedata = {
        prdName: editlive.prdName,
        prdNo: editlive.prdNo,
        prdPhoto: this.prdPhoto,
        prdCategory: this.items.indexOf(this.select),
        liveTitle: editlive.liveTitle,
        prdPriceStart: editlive.prdPriceStart,
        liveDesc: editlive.liveDesc,
        liveDate: this.date,
        liveTime: this.time
      }
      console.log(editlivedata)
      console.log(editlive)
      console.log(editlive.prdPhoto)
      rest.
        axios({
          method: 'put',
          url: `/dabid/live/${prdId}`,
          data: editlivedata,
          headers: config,
        })
        .then((res) => {
          console.log(res)
          this.$router.push({ name: 'MyLiveList', params: { prdId: `${prdId}` }})
        })
        .catch((err) => {
          console.log('안됨..')
          console.log('수정실패',err)
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
    },
  },
  mounted() {
    this.calcDate()
  },
  created: function () {
    if (localStorage.getItem("jwt")) {
      this.getLive();
    } else {
      this.$router.push({ name: "Login" });
    }
  },
}
</script>