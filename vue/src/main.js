import Vue from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
// import Vuetify from 'vuetify/lib'
import './permission'
import Vuelidate from 'vuelidate'

// 解决登录报错的问题
import Router from 'vue-router'
Vue.config.productionTip = false
const routerPush = Router.prototype.push
Router.prototype.push = function push (location) {
  return routerPush.call(this, location).catch(error => error)
}

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')

Vue.use(Vuelidate)

// Vue.use(Vuetify)

// export default new Vuetify({
//   theme: { dark: true }
// })
