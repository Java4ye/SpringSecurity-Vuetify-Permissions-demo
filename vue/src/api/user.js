import request from '@/utils/request'

export function login (data) {
  return request({
    url: '/api/mock/login',
    method: 'post',
    data
  })
}

export function getInfo () {
  return request({
    url: '/api/mock/userinfo',
    method: 'get'
  })
}

export function logout () {
  return request({
    url: '/api/logout',
    method: 'post'
  })
}
