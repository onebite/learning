import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'

//create an axios instance
const service = axios.create({
    baseURL: process.env.BASE_API,//DefinePlugin Setting
    timeout: 5000
})

//request interceptor
service.interceptors.request.use(
    config => {
        if (store.getters.token){
            config.headers['Zero-Token'] = getToken()
        }

        return config
    },
    error => {
        console.log(error)
        Promise.reject(error)
    }
)

// response interceptor
service.interceptors.response.use(
    // response => response,
    response => {
        const res = response.data
        if (res.code !== 20000){
            Message({
                message: res.message,
                type: 'error',
                duration: 5 * 1000
            })
            //or fetch a method
            if (res.code === 50008 || res.code === 50012) {
                MessageBox.confirm('你已被登出，可以取消继续留在该页面，或者重新登录', '确定登出',{
                    confirmButtonText: '重新登录',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    store.dispatch('FedLogOut').then(() => { location.reload()})
                })
            }

            return Promise.reject('error')
        } else {
            return response.data
        }
    },
    error => {
        console.log('err' + error)
        Message({
            message: error.message,
            type: 'error',
            duration: 5 * 1000
        })

        return Promise.reject(error)
    }
)

export default service