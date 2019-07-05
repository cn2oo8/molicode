import axiosInstance from '@/request/axiosInstance'

var _ = require('underscore')

var requestUtils = {
    getSubmit: function (_vue, url, param, successHandler, errorHandler, loading) {
        let loadingKey = 'loading'
        if (loading == null || loading === false) {
            loadingKey = null
        } else if (loading && _.isString(loading)) {
            loadingKey = loading
        }
        if (loading && _vue[loadingKey] != null) {
            _vue[loadingKey] = true
        }
        axiosInstance.get(url, param).then(response => {
            if (loadingKey && loadingKey !== '') {
                _vue[loadingKey] = false
            }
            var _this = _vue == null ? this : _vue
            successHandler.call(_this, response.data);
        }, error => {
            console.log(error)
            var data = {'success': false, 'message': '服务器异常，请联系管理员或重试！'}
            requestUtils.fProcessResult(_vue, data, successHandler, errorHandler, loadingKey)
        })
    },
    postSubmit: function (_vue, url, param, successHandler, errorHandler, loading) {
        let loadingKey = 'loading'
        if (loading == null || loading === false) {
            loadingKey = null
        } else if (loading && _.isString(loading)) {
            loadingKey = loading
        }
        if (loading && _vue[loadingKey] != null) {
            _vue[loadingKey] = true
        }
        axiosInstance.post(url, param).then(response => {
            requestUtils.fProcessResult(_vue, response.data, successHandler, errorHandler, loadingKey)
        }, error => {
            console.log(error)
            var data = {'success': false, 'message': '服务器异常，请联系管理员或重试！'}
            requestUtils.fProcessResult(_vue, data, successHandler, errorHandler, loadingKey)
        })
    },
    postSubmitByConfig: function (_vue, url, param, successHandler, errorHandler, loading, config) {
        let loadingKey = 'loading'
        if (loading == null || loading === false) {
            loadingKey = null
        } else if (loading && _.isString(loading)) {
            loadingKey = loading
        }
        if (config === null || config === false) {
            config = {}
        }
        if (loading && _vue[loadingKey] != null) {
            _vue[loadingKey] = true
        }
        axiosInstance.post(url, param, config).then(response => {
            requestUtils.fProcessResult(_vue, response.data, successHandler, errorHandler, loadingKey)
        }, error => {
            console.log(error)
            var data = {'success': false, 'message': '服务器异常，请联系管理员或重试！'}
            requestUtils.fProcessResult(_vue, data, successHandler, errorHandler, loadingKey)
        })
    },
    fProcessResult: function (_vue, data, successHandler, errorHandler, loadingKey) {
        if (loadingKey && loadingKey !== '') {
            _vue[loadingKey] = false
        }
        var _this = _vue == null ? this : _vue
        if (data) {
            if (data['success'] && data['success'] === true) {
                successHandler.call(_this, data)
            } else {
                // 登录码
                if (data['returnCode'] === '120' && _vue) {
                    _vue.$router.push({name: 'login'});
                }
                if (errorHandler) {
                    errorHandler.call(_this, data)
                } else {
                    var sMessage = data['message']
                    if (!sMessage || sMessage === '') {
                        sMessage = '处理异常，请联系管理员！'
                    }
                    _vue.$Modal.error({
                        title: '错误',
                        content: sMessage
                    });
                    _vue.$Message.error({
                        content: sMessage,
                        duration: 10
                    });

                }
            }
        }
    },
    serializeObject: function (params, removeNullField, trim) {
        var o = {}
        if (params === null || params === undefined) {
            return o
        }
        let keys = _.keys(params)
        for (var index = 0; index < keys.length; index++) {
            var key = keys[index]
            var value = params[key]
            if (value && trim && _.isString(value)) {
                value = value.trim()
            }
            if (removeNullField && (value == null || value === '')) {
                continue
            } else {
                o[key] = value
            }
        }
        return o
    }
}
export default requestUtils
