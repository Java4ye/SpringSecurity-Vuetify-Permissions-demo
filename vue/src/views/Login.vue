<template>
  <div>
    <v-app>
      <!-- <v-snackbar
      :timeout="-1"
      :value="true"
      absolute
      color="red accent-2"
      rounded="lg"
      top
    >
      Lorem ipsum dolor sit amet consectetur.
    </v-snackbar> -->
      <v-main class="primary login-background"> </v-main>
      <v-main style="z-index: 1">
        <v-container fill-height="fill-height">
          <v-layout align-center justify-center>
            <v-flex class="login-form text-sm-center">
              <div class="mb-3 text-h4">
                <v-icon class="mr-lg-1" :large="true">{{
                  'mdi-briefcase'
                }}</v-icon>
                Hello
              </div>
              <v-card light>
                <v-card-text>
                  <div class="subtitle-1">
                    <template v-if="options.isLoggingIn"
                      >Log in to your customer portal</template
                    >
                    <template v-else>Crate a new account</template>
                  </div>
                  <!-- :rules="nameRules" -->
                  <v-form v-model="valid" lazy-validation>
                    <v-text-field
                      ref="username"
                      v-model="user.name"
                      light
                      prepend-icon="mdi-account"
                      label="Name"
                      :counter="10"
                      required
                      autofocus
                      :error-messages="nameErrors"
                      @input="$v.user.name.$touch()"
                      @blur="$v.user.name.$touch()"
                    ></v-text-field>

                    <!-- :rules="emailRules" -->

                    <v-text-field
                      ref="email"
                      v-show="!options.isLoggingIn"
                      v-model="user.email"
                      light
                      prepend-icon="mdi-email"
                      type="email"
                      label="E-mail"
                      required
                      :error-messages="emailErrors"
                      @input="$v.user.email.$touch()"
                      @blur="$v.user.email.$touch()"
                    ></v-text-field>

                    <v-text-field
                      ref="password"
                      v-model="user.password"
                      light
                      prepend-icon="mdi-lock"
                      label="Password"
                      type="password"
                      :error-messages="passwordErrors"
                      required
                      @input="$v.user.password.$touch()"
                      @blur="$v.user.password.$touch()"
                    ></v-text-field>

                    <!-- :rules="[(v) => !!v || 'You must agree to continue!']" -->

                    <v-checkbox
                      v-if="options.isLoggingIn"
                      v-model="checkbox"
                      light
                      label="Stay logged in?"
                      required
                      hide-details="auto"
                      :error-messages="checkboxErrors"
                      @change="$v.checkbox.$touch()"
                      @blur="$v.checkbox.$touch()"
                    ></v-checkbox>

                    <v-btn
                      class="mt-2"
                      v-if="options.isLoggingIn"
                      @click.prevent="handleLogin"
                      light
                      :block="true"
                      type="submit"
                      :loading="btnLoading"
                      >Sign in</v-btn
                    >
                    <v-btn
                      v-else
                      light
                      :block="true"
                      type="submit"
                      @click.prevent="options.isLoggingIn = true"
                      >Sign up</v-btn
                    >
                  </v-form>
                </v-card-text>
              </v-card>

              <div v-if="options.isLoggingIn" class="mt-2">
                Don't have an account?
                <v-btn light @click="options.isLoggingIn = false"
                  >Sign up</v-btn
                >
              </div>
            </v-flex>
          </v-layout>
        </v-container>
      </v-main>
      <v-main class="login-background"> </v-main>
      <v-footer>
        <v-flex class="text-xs-center">© 2018. All rights reserved.</v-flex>
      </v-footer>
    </v-app>
  </div>
</template>
<script>
import { validationMixin } from 'vuelidate'
import { required, maxLength, email } from 'vuelidate/lib/validators'

export default {
  name: 'Login',
  mixins: [validationMixin],
  validations: {
    user: {
      name: { required, maxLength: maxLength(10) },
      email: { required, email },
      password: { required }
    },

    checkbox: {
      checked (val) {
        console.log(val)
        return val
      }
    },
    validationLoginGroup: ['user.name', 'user.password', 'checkbox'],
    validationRegistryGroup: ['user']
  },
  created () {
    // this.$vuetify.theme.dark = true

  },
  computed: {
    checkboxErrors () {
      const errors = []
      if (!this.$v.checkbox.$dirty) return errors
      !this.$v.checkbox.checked && errors.push('You must agree to continue!')
      return errors
    },
    passwordErrors () {
      const errors = []
      if (!this.$v.user.password.$dirty) return errors
      !this.$v.user.password.required && errors.push('Password is required.')
      return errors
    },
    nameErrors () {
      const errors = []
      if (!this.$v.user.name.$dirty) return errors
      !this.$v.user.name.maxLength &&
        errors.push('Name must be at most 10 characters long')
      !this.$v.user.name.required && errors.push('Name is required.')
      return errors
    },
    emailErrors () {
      const errors = []
      if (!this.$v.user.email.$dirty) return errors
      !this.$v.user.email.email && errors.push('Must be valid e-mail')
      !this.$v.user.email.required && errors.push('E-mail is required')
      return errors
    }
  },
  data () {
    return {
      user: {
        email: '',
        password: '123456',
        name: 'admin'
      },
      checkbox: true,

      btnLoading: false,
      valid: true,
      /*  nameRules: [
        v => !!v || 'Name is required',
        v => (v && v.length <= 10) || 'Name must be less than 10 characters'
      ],
      emailRules: [
        v => !!v || 'E-mail is required',
        v => /.+@.+\..+/.test(v) || 'E-mail must be valid'
      ], */
      options: {
        isLoggingIn: true
      },

      redirect: undefined,
      otherQuery: {}
    }
  },
  watch: {
    $route: {
      handler: function (route) {
        const query = route.query
        if (query) {
          this.redirect = query.redirect
          this.otherQuery = this.getOtherQuery(query)
        }
      },
      immediate: true
    }
  },
  methods: {
    /*  validate () {
      this.$refs.user.validate()
    }, */
    handleLogin () {
      // Sets the $dirty flag of the model and all its children to true recursively.
      console.log('submit!')
      this.$v.validationLoginGroup.$touch()
      if (this.$v.validationLoginGroup.$invalid) {
        console.log('error!')
        this.btnLoading = false
        return false
      } else {
        // do your submit logic here
        console.log('submit!')
        this.btnLoading = true
        setTimeout(() => {
          this.$store
            .dispatch('user/login', this.user)
            .then(() => {
              this.btnLoading = false
              console.log(this.redirect)
              this.$router.push({
                path: this.redirect || '/',
                query: this.otherQuery
              })
            })
            .catch(() => {
              this.btnLoading = false
            })
        }, 500)
      }
    },
    getOtherQuery (query) {
      return Object.keys(query).reduce((acc, cur) => {
        if (cur !== 'redirect') {
          acc[cur] = query[cur]
        }
        return acc
      }, {})
    }
  }
}
</script>
<style >
.login-form {
  max-width: 500px;
}
.login-background {
  height: 50%;
  width: 100%;
  position: absolute;
  top: 0;
  left: 0;
  z-index: 0;
}
</style>
