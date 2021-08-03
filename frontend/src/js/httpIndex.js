import axios from "axios"
import store from "../store"

const http = axios.create({
  baseURL: "https://testrestapi.cafe24.com",
  headers: { "content-type": "application/json" },
})

http.interceptors.request.use(
  config => {
    const isAuthenticated = store.getters["isAuthenticated"]
    if (isAuthenticated) {
      config.headers.common["Authorization"] = store.getters["getAccessToken"]
    }
    return config
  },
  error => {
    // Do something with request error
    Promise.reject(error)
  }
)
http.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded"

export default http