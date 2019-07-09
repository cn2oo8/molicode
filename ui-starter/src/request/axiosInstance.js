import iView from 'iview'
import axios from 'axios'
import dateUtil from 'iview/src/utils/date'
import Qs from 'qs'
import router from '@/router'
import * as mock from '@/mock/mock.js'

const endsWith = (str, suffix) => {
    return str.indexOf(suffix, str.length - suffix.length) !== -1
}
const axiosInstance = axios.create({
    transformRequest: [function (data) {
        if (data != null) {
            Object.keys(data).forEach((key) => {
                let val = data[key]
                if (val instanceof Date) {
                    let fmt = 'yyyy-MM-dd HH:mm:00'
                    if (endsWith(key, 'End')) {
                        fmt = 'yyyy-MM-dd HH:mm:59'
                    }
                    let str = dateUtil.format(val, fmt)
                    data[key] = str
                }
            })
        }
        let postData = Qs.stringify(data)
        return postData
    }],
    timeout: 1800000,
    headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
    }
})

axiosInstance.interceptors.request.use(config => {
    //  console.log(config.url)
    iView.LoadingBar.start()
    return config
}, error => {
    iView.LoadingBar.error()
    iView.Message.error('请求异常！' + error)
    return Promise.reject(error)
})

axiosInstance.interceptors.response.use(response => {
    iView.LoadingBar.finish()
    if (response.data.success === false && (
        response.data.returnCode === 'PERMISSION_DENIED' ||
        response.data.returnCode === 'NOT_LOGIN' ||
        response.data.returnCode === 'DATA_NOT_VISIBLE'
    )) {
        if (response.data.returnCode === 'PERMISSION_DENIED') {
            router.replace({
                path: '/v/noAuth',
                query: {message: response.data.message}
            })
        } else if (response.data.returnCode === 'NOT_LOGIN') {
            //  window.location = cfs.getLoginUrl(response.data.loginAddress)
        } else if (response.data.returnCode === 'DATA_NOT_VISIBLE') {
            router.replace({
                path: '/v/error',
                query: {message: response.data.message, type: response.data.type, id: response.data.id}
            })
        }
        return Promise.reject(response)
    }
    return response
}, error => {
    console.log(error)
    // window.location.href = location.href
    iView.LoadingBar.error()
    iView.Notice.error({
        desc: '数据查询失败，请检查WiFi或者网络是否正常！' + error.message
    })
    // 这里我们把错误信息扶正, 后面就不需要写 catch 了
    return Promise.reject(error)
})

//mock.mock(axiosInstance)
export default axiosInstance
