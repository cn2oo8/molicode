import Cookies from 'js-cookie';
import constants from '@/constants/constants';
import requestUtils from '@/request/requestUtils.js';

const user = {
    state: {
        userInfo: null,
        login: false
    },
    mutations: {
        logout(state, vm) {
            Cookies.remove('access');
            // 恢复默认样式
            let themeLink = document.querySelector('link[name="theme"]');
            themeLink.setAttribute('href', '');
            // 清空打开的页面等数据，但是保存主题数据
            let theme = '';
            if (localStorage.theme) {
                theme = localStorage.theme;
            }
            localStorage.clear();
            if (theme) {
                localStorage.theme = theme;
            }
            this.login = false;
            this.userInfo = null;
            requestUtils.postSubmit(vm, constants.urls.sys.acUser.logout, {}, function (data) {
            });
            Cookies.remove('moliLoginKey');
        },
        [constants.types.SET_LOGIN_USER]: function (state, payload) {
            state.login = true;
            state.userInfo = payload['userInfo'];
        }
    },
    actions: {
        [constants.types.LOAD_LOGIN_USER]: ({state, commit, dispatch}, payload) => {
            var searchParam = {'t': new Date().getTime()};
            var _this = payload['_vue'] ? payload['_vue'] : this;
            return new Promise((resolve, reject) => {
                if (state.login) {
                    resolve(state.userInfo);
                    return;
                }
                requestUtils.postSubmit(_this, constants.urls.sys.acUser.getLoginUser, searchParam, function (data) {
                    if (data.value) {
                        commit(constants.types.SET_LOGIN_USER, {userInfo: data.value, _vue: _this})
                        state.userInfo = data.value;
                        resolve(data.value);
                    } else {
                        resolve(null);
                    }
                }, function (data) {
                    reject(new Error('Could not get login User'));
                }, false);
            });
        },
        [constants.types.CHANGE_PASSWORD]: ({state, commit, dispatch}, payload) => {
            var _this = payload['_vue'] ? payload['_vue'] : this;
            var loadingKey = payload['loadingKey'];
            return new Promise((resolve, reject) => {
                requestUtils.postSubmit(_this, constants.urls.sys.acUser.changePassword, payload['data'], function (data) {
                    resolve(data);
                }, null, loadingKey);
            });
        }
    }
};

export default user;
