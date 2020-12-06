<!--
 * @Descripttion    : 登录页
 * @Author          : RyzeYang
 * @version         : 1.0
 * @Date            : 2020-11-04 22:59:18
 * @LastEditors     : RyzeYang
 * @LastEditTime    : 2020-11-21 11:10:22
-->
@after-appear=""
<template>
  <div>
    <v-app>
      <v-content>
        <v-container fill-height="fill-height">
          <v-layout align-center justify-center>
            <v-flex class="login-form text-sm-center">
              <div class="mb-3 text-h4">
                <v-icon class="mr-lg-1" :large=true>{{
                  'mdi-briefcase'
                }}</v-icon>
                MyWorkspace
              </div>
              <v-card :light=true>
                <v-card-text>
                  <div class="subtitle-1">
                    <template v-if="options.isLoggingIn"
                      >Log in to your customer portal</template
                    >
                    <template v-else>Crate a new account</template>
                  </div>
                  <v-form v-model="valid" lazy-validation>
                    <v-text-field
                      v-show="!options.isLoggingIn"
                      v-model="user.name"
                     :light=true
                      prepend-icon="mdi-account"
                      label="Name"
                      :counter="10"
                      :rules="nameRules"
                      required
                    ></v-text-field>

                    <v-text-field
                      v-model="user.email"
                     :light=true
                      prepend-icon="mdi-email"
                      type="email"
                      :rules="emailRules"
                      label="E-mail"
                      required
                    ></v-text-field>

                    <v-text-field
                      v-model="user.password"
                     :light=true
                      prepend-icon="mdi-lock"
                      label="Password"
                      type="password"
                    ></v-text-field>

                    <v-checkbox
                      v-if="options.isLoggingIn"
                      v-model="options.shouldStayLoggedIn"
                     :light=true
                      label="Stay logged in?"
                      :rules="[(v) => !!v || 'You must agree to continue!']"
                      required
                      hide-details="auto"
                    ></v-checkbox>

                    <v-btn
                      class="mt-2"
                      v-if="options.isLoggingIn"
                      @click.prevent=""
                     :light=true
                      block="true"
                      type="submit"
                      >Sign in</v-btn
                    >
                    <v-btn
                      v-else
                      block="true"
                     :light=true
                      type="submit"
                      @click.prevent="options.isLoggingIn = true"
                      >Sign up</v-btn
                    >
                  </v-form>
                </v-card-text>
              </v-card>

              <div v-if="options.isLoggingIn" class="mt-2">
                Don't have an account?
                <v-btn :light=true @click="options.isLoggingIn = false"
                  >Sign up</v-btn
                >
              </div>
            </v-flex>
          </v-layout>
        </v-container>
      </v-content>
      <v-footer>
        <v-flex class="text-xs-center">© 2018. All rights reserved.</v-flex>
      </v-footer>
    </v-app>
  </div>
</template>
<script>
export default {
  name: 'Login2',
  created () {
    this.$vuetify.theme.dark = true
  },
  data () {
    return {
      user: {
      // email: 'admin@example.com',
      // password: 'admin',
      // name: 'John Doe',
      },
      valid: true,
      nameRules: [
        v => !!v || 'Name is required',
        v => (v && v.length <= 10) || 'Name must be less than 10 characters'
      ],
      emailRules: [
        v => !!v || 'E-mail is required',
        v => /.+@.+\..+/.test(v) || 'E-mail must be valid'
      ],
      options: {
        isLoggingIn: true,
        shouldStayLoggedIn: true
      }
    }
  }
}
</script>
<style >
.login-form {
  max-width: 500px;
}
</style>
