<!--
 * @Descripttion    : 入口页面
 * @Author          : RyzeYang
 * @version         : 1.0
 * @Date            : 2020-11-03 22:15:46
 * @LastEditors     : RyzeYang
 * @LastEditTime    : 2020-11-05 06:43:19
-->
<template>
  <!-- App.vue -->

  <v-app>

    <!-- 根据应用程序组件调整内容大小 -->
    <v-main >
      <!-- 为应用程序提供正确的插槽 -->
      <v-container fluid class="pa-0">
        <!-- 如果使用vue-router -->
        <router-view></router-view>
        <Snackbar />
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import Snackbar from '@/components/Snackbar'
export default {
  name: 'App',
  components: {
    Snackbar
  },
  data () {
    return {
      drawer: null,
      items: [
        { title: 'Dashboard', icon: 'mdi-view-dashboard' },
        { title: 'Photos', icon: 'mdi-image' },
        { title: 'About', icon: 'mdi-help-box' }
      ]
    }
  },
  created () {
    // 在页面加载时读取sessionStorage里的状态信息
    if (sessionStorage.getItem('store')) {
      this.$store.replaceState(
        Object.assign(
          {},
          this.$store.state,
          JSON.parse(sessionStorage.getItem('store'))
        )
      )
    }
    // 在页面刷新时将vuex里的信息保存到sessionStorage里
    window.addEventListener('beforeunload', () => {
      sessionStorage.setItem('store', '')
      sessionStorage.setItem('store', JSON.stringify(this.$store.state))
    })
  }

}
</script>
