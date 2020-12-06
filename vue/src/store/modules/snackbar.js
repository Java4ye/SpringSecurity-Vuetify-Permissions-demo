/**
 * @param msg 信息
 * @param color snackbar 颜色
 * @param visible 是否可见
 * @param showClose 关闭按钮
 * @param timeout 停留持续时间
 */
const snackbar = {
  //
  namespaced: true,
  state: {
    msg: '',
    color: '',
    visible: false,
    showClose: true,
    timeout: 5000

  },
  // 逻辑处理,同步函数
  mutations: {
    OPEN_SNACKBAR (state, options) {
      state.visible = true
      state.msg = options.msg
      state.color = options.color
    },
    CLOSE_SNACKBAR (state) {
      state.visible = false
    },
    setShowClose (state, isShow) {
      state.showClose = isShow
    },
    setTimeout (state, timeout) {
      state.timeout = timeout
    }
  },
  // 逻辑处理,异步函数
  actions: {
    openSnackbar (context, options) {
      console.log(options)

      const timeout = context.state.timeout
      context.commit('OPEN_SNACKBAR', {
        msg: options.msg,
        color: options.color
      })
      setTimeout(() => {
        context.commit('CLOSE_SNACKBAR')
      }, timeout)
    }
  }
}
export default snackbar
