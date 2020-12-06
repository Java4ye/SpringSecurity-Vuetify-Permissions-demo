/*
 * @Descripttion    :
 * @Author          : RyzeYang
 * @version         :
 * @Date            : 2020-12-03 07:17:57
 * @LastEditors     : RyzeYang
 * @LastEditTime    : 2020-12-03 07:50:16
 */

const TokenKey = 'token'

export function getToken () {
  console.log(TokenKey)
  return sessionStorage.getItem(TokenKey)
}

export function setToken (token) {
  console.log(TokenKey, token)
  return sessionStorage.setItem(TokenKey, token)
}

export function removeToken () {
  return sessionStorage.removeItem(TokenKey)
}
