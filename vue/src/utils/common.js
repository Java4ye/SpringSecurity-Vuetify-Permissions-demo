import store from '@/store'

export function errorTip (msg) {
  console.log(msg)
  store.dispatch('snackbar/openSnackbar', {
    msg: msg,
    color: 'red accent-2'
  })
}
