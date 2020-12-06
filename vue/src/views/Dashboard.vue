<!--
 * @Descripttion    : 入口页面
 * @Author          : RyzeYang
 * @version         : 1.0
 * @Date            : 2020-11-03 22:15:46
 * @LastEditors     : RyzeYang
 * @LastEditTime    : 2020-12-04 22:15:35
-->
<template>
  <!-- App.vue -->
  <v-app>
    <v-parallax src="@/assets/img/background.png"> </v-parallax>

    <!-- <v-system-bar app window>

      <v-spacer></v-spacer>

      <v-icon>mdi-square</v-icon>

      <v-icon>mdi-circle</v-icon>

      <v-icon>mdi-triangle</v-icon>
    </v-system-bar> -->

    <v-app-bar app>
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>

      <v-toolbar-title>Application</v-toolbar-title>
    </v-app-bar>

    <v-navigation-drawer
      v-model="drawer"
      app
      overlay-color="blue"
      src="@/assets/img/navigater.png"
    >
      <v-list-item>
        <v-list-item-content>
          <v-list-item-title class="title"> Application </v-list-item-title>
          <v-list-item-subtitle> subtext </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>
      <template v-slot:append>
        <div class="pa-2">
          <v-btn block
           elevation="17"
           style="background-color: rgb(3, 25, 49);color:white"
          @click.native="logout">Logout</v-btn>
        </div>
      </template>
      <template v-slot:prepend>
        <div class="pa-2">
          <v-list-item-avatar>
            <v-img
              :src="require('../assets/img/'+imgSrc+'.png')"
            ></v-img>
          </v-list-item-avatar>
        </div>
      </template>
      <v-divider></v-divider>

      <v-list dense nav>
        <v-list-item v-for="item in items" :key="item.title" link :href="item.href">
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
      <!-- -->
    </v-navigation-drawer>

    <!-- 根据应用程序组件调整内容大小 -->
    <v-main class="pa-0">
      <!-- 为应用程序提供正确的插槽 -->
      <v-container fluid>
        <!-- 如果使用vue-router -->
        <router-view></router-view>
      </v-container>
    </v-main>

    <v-footer app>

      <!-- -->
    </v-footer>
  </v-app>
</template>

<script>

export default {
  name: 'App',
  data () {
    return {
      drawer: null,
      imgSrc: null,
      items: []
    }
  },
  created () {
    const roles = this.$store.state.user.roles
    console.log(roles)
    if (roles.includes('admin')) {
      this.imgSrc = 'admin'
    } else {
      this.imgSrc = 'user'
    }

    // TODO 简单版本 后期会升级 菜单等路由信息还是要后台返回才行 打算做个多级菜单
    const base = [
      { title: 'Dashboard', icon: 'mdi-view-dashboard', href: '/' }
    ]
    const arr = [
      { title: 'Autocompletes', icon: 'mdi-image', href: '/a' },
      { title: 'Table', icon: 'mdi-help-box', href: '/t' }
    ]
    // 拥有的路由
    const routes = this.$store.state.permission.addRoutes[0].children
    const curRoutes = []
    routes.forEach(element => {
      for (let i = 0; i < arr.length; i++) {
        if (arr[i].href === element.path) {
          curRoutes.push(arr[i])
          break
        }
      }
    })
    console.log(curRoutes)

    this.items = base.concat(curRoutes)
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
  },
  methods: {
    async logout () {
      await this.$store.dispatch('user/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    }
  }

}
</script>
