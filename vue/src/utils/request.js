import axios from 'axios'
// import axiosRetry from 'axios-retry'
import { errorTip } from '@/utils/common'

// import { MessageBox, Message } from 'element-ui'
// import store from '@/store'
// import { getToken, removeToken, setToken } from '@/utils/auth'
import { getToken } from '@/utils/auth'
// import { errmsg } from '@/utils/errMsg'

const apiHost = '/'
// var isRelogin = false
/* function relogin () {
  MessageBox.confirm('妳已被登出，可以取消繼續留在該頁面，或者重新登錄', '確定登出', {
    confirmButtonText: '重新登錄',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    removeToken()
    window.location.assign('/view')
  })
} */

/* function getNewToken ({ JwtToken = getToken() } = {}) {
  axios({
    url: '/api/Login/tokenRenew',
    method: 'post',
    data: { JwtToken }
  }).then((e) => {
    console.log(e)
    if (e.data.StatusCode === '000') {
      setToken(e.data.RetData)
    } else {
      isRelogin = true
      relogin()
    }
  }).catch((e) => {
    console.log(e)
  })
} */
console.log(apiHost)
// create an axios instance
const service = axios.create({
  baseURL: apiHost, // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  // auth: 'Bearer ' + getToken(),
  timeout: 1000 * 90 // request timeout
})
/* axiosRetry(service, {
  retries: 1, // 设置自动发送请求次数
  retryDelay: (retryCount) => {
    console.log(retryCount)
    return retryCount * 500
  },
  retryCondition: (err) => {
    if (err.response.status === 406 || err.response.data.StatusCode === 'E003') { // token expired
      getNewToken()
      console.log('--------------------', isRelogin)

      if (!isRelogin) {
        return true
      } else {
        return false
      }
    } else if (err.response.data.StatusCode === 'E005' || err.response.data.StatusCode === 'E002') {
      relogin()
    }
    return false
  }
}) */

service.interceptors.request.use(
  config => {
  // do something before request is sent
    config.headers.Authorization = 'Bearer ' + getToken()
    return config
  },
  error => {
    console.log(error) // for debug
    console.log(error.response) // for debug
    /* const res = error.response.data || {}
     if (res.StatusCode === 'E003') { // token expired
      getNewToken()
      return
    } else if (res.StatusCode === 'E002' || res.StatusCode === 'E005') {
      relogin()
    } */
    // Message({
    //   message: errmsg.get(res.StatusCode) || res.ErrorMessage || 'Error',
    //   type: 'error',
    //   duration: 5 * 1000
    // })
    return Promise.reject(error)
  }
)

// response interceptor
service.interceptors.response.use(
  response => {
    console.log(response)
    const res = response.data

    const code = res.code

    // 成功就直接返回res
    if (code === '00000') {
      return res
    } else {
      console.log('弹出提示框 ' + res)
      errorTip(res.msg)
    }
  },
  error => {
    console.log(error) // for debug
    console.log(error.response) // for debug
    // const res = error.response
    const data = error.response.data || {}
    console.log(data) // for debug
    /*   if (data.StatusCode === 'E003') { // token expired
      // getNewToken()
      return
    } else if (res.status === 406 || data.StatusCode === 'E002' || data.StatusCode === 'E005' || (error.response.status === 400 && error.response.headers['content-type'].includes('application/json'))) {
      relogin()
      return
    } */
    // Message({
    //   message: errmsg.get(data.StatusCode) || data.ErrorMessage || 'Error',
    //   type: 'error',
    //   duration: 5 * 1000
    // })
    return Promise.reject(error)
  }
)

export default service
