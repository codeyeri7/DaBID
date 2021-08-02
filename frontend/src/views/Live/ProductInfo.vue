<template>
  <v-form
    ref="form"
    v-model="valid"
    lazy-validation
  >
    <v-text-field
      v-model="productName"
      :counter="20"
      :rules="nameRules"
      label="상품명"
      placeholder="정확한 상품명을 입력해주세요"
      required
    ></v-text-field>

    <v-text-field
      v-model="productNumber"
      :rules="productNumberRules"
      label="일련 번호"
      placeholder="xxx-xxxx 형식으로 입력해주세요"
      required
    ></v-text-field>

    <v-file-input
      label="File input"
      filled
      prepend-icon="mdi-camera"
    ></v-file-input>

    <v-select
      v-model="select"
      :items="items"
      :rules="[v => !!v || 'Item is required']"
      label="Item"
      required
    ></v-select>
    <RouterLink :to="{ name: 'LiveInfo' }">
      <v-btn
        class="mr-4"
        type="submit"
        :disabled="invalid"
        @click="productInfoSubmit"
      >
        다음
      </v-btn>
    </RouterLink>
  </v-form>
</template>

<script>
import axios from 'axios'
  export default {
    name: 'ProductInfo',
    data: () => ({
      valid: true,
      productName: '',
      nameRules: [
        v => !!v || 'Name is required',
        v => (v && v.length <= 20) || 'Name must be less than 20 characters',
      ],
      productNumber: '',
      productNumberRules: [
        v => !!v || 'productNumber is required',
        v => /.+@.+\..+/.test(v) || 'productNumber must be valid',
      ],
      productPhoto: [],
      select: null,
      items: [
        '의류',
        '가방',
        '신발',
        '악세사리',
      ],
    }),

    methods: {
      productInfoSubmit() {
        const product = {
          productName: this.productName,
          productNumber: this.productNumber,
          productPhoto: this.productPhoto,
          items: this.items,
        }
        console.log(product)
        if (product.productName) {
          axios.post(`http://localhost:8080/LiveInfo`, product)
            .then(() => {
              this.$emit('liveInfo')
              this.productName = ''
              this.productNumber = ''
              this.productPhoto = []
              this.items = ''
            })
        }
      },
    },
  }
</script>

<style>

</style>