import Vue from "vue"
import Vuex from "vuex"

Vue.use(Vuex)

import jwt from "../js/jwt.js"
import http from "../js/httpIndex.js"

export default new Vuex.Store({
    //count state 속성 추가 
    state: {
        count: 0, // count 를 0 으로 초기화
        token: {
          accessToken: jwt.getToken(),
        }, // 토큰정보
        isAuthenticated: !!jwt.getToken(),
      },
      getters: {
        getAccessToken: function (state) {
          return state.token.accessToken
        },
        isAuthenticated: function (state) {
          return state.isAuthenticated
        },
      },
      mutations: {
        logout: function (state, payload = {}) {
          state.token.accessToken = ""
          state.isAuthenticated = false
          jwt.destroyToken()
        },
        login: function (state, payload = {}) {
          state.token.accessToken = payload.accessToken
          state.isAuthenticated = true
          jwt.saveToken(payload.accessToken)
        },
      },
      actions: {
        logout: function (context, payload) {
          return new Promise(resolve => {
            setTimeout(function () {
              context.commit("logout", payload)
              resolve({})
            }, 1000)
          })
        },
        register: function (context, payload) {
          let params = {
            name: payload.userName,
          }
          return new Promise((resolve, reject) => {
            http
              .post("/api/sample/register", params)
              .then(response => {
                const { data } = response
                context.commit("login", {
                  accessToken: data.accessToken,
                })
    
                resolve(response)
              })
              .catch(error => {
                reject(error)
              })
          })
        },
        login: function (context, payload) {
          let params = {
            name: payload.userName
          }
          return new Promise((resolve, reject) => {
            http
              .post("/api/sample/login", params)
              .then(response => {
                const { data } = response
                context.commit("login", {
                  accessToken: data.accessToken,
                })
    
                resolve(response)
              })
              .catch(error => {
                reject(error)
              })
          })
        },
      },
      modules: { coffee, shop },
})