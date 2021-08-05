import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import router from './router/index'



Vue.config.productionTip = false


import GAuth from 'vue-google-oauth2'
const gauthOption = {
  clientId: '771637237260-gsvimko90s79m4u8co55gaqaqnplj9ne.apps.googleusercontent.com',
  scope: 'profile email',
  prompt: 'consent',
  // prompt: 'select_accout'
  fetch_basic_profile: true
}
Vue.use(GAuth, gauthOption)
new Vue({
  vuetify,
  router,
  render: h => h(App)
}).$mount('#app')

var filter = function(text, length, clamp){
  clamp = clamp || '...';
  var node = document.createElement('div');
  node.innerHTML = text;
  var content = node.textContent;
  return content.length > length ? content.slice(0, length) + clamp : content;
};

Vue.filter('truncate', filter);

