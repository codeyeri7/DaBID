<template>
  <div class=" d-flex align-center text-center mx-3 mt-6">
    <v-card class="ap-card" >
      <v-card-title tile class="text-h5"
      style="color:#ebe2de; background-color: #292938">
        Accident Policy
      </v-card-title>

      <v-card-text class="card-text" style="margin-top: 2.5rem" id="kor-font">
        다비드는 원활한 거래 연결을 위해<br>
        더치트 계좌 조회 서비스를 지원합니다.
        <v-btn tile class="mt-4 link-button" onclick="window.open('https://thecheat.co.kr/rb/?r=home&mod=_thecheat_validity_account') ">위험 계좌조회 더치트 바로가기</v-btn>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>
        <RouterLink :to="{ name: 'MyPage' }">
          <v-btn text id="kor-font">
            확인
          </v-btn>
        </RouterLink>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
export default {
  name: 'TheCheat',
}
</script>

