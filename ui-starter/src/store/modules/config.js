import constants from '@/constants/constants';
import requestUtils from '@/request/requestUtils.js';
import * as configUtil from '@/libs/configUtil.js'

const autoCode = {
    state: {
        bindMap: {
            'autoCodeReplaceUtil': null
        }
    },
    mutations: {},
    getters: {
        getConfigObject: (state, getters) => {
            return function (configKey) {
                return configUtil.getConfigObject(state.defaultProjectConfig, configKey);
            };
        },
        getConfigObjectVal: (state, getters) => {
            return function (configKey, bizKey) {
                return configUtil.getConfigObjectVal(state.defaultProjectConfig,
                    configKey, bizKey);
            };
        }
    },
    actions: {
        /**
         * 加载bindResource
         * @param state
         * @param commit
         * @param dispatch
         * @param payload 载荷
         * @returns {*}
         */
        [constants.types.GET_BIND_RESOURCE]: ({state, commit, dispatch}, payload) => {
            var searchParam = {
                bindId: payload['bindId']
            };
            var _this = payload['_vue'] !== null ? payload['_vue'] : this;
            return new Promise((resolve, reject) => {
                requestUtils.postSubmit(_this, constants.urls.common.bindResource.getBindResource, searchParam, function (data) {
                    if (data.value) {
                        state.bindMap[searchParam.bindId] = data.value;
                    }
                    resolve(data.value);
                }, function (data) {
                    reject(new Error('Could not get bindResource, bindId=' + searchParam.bindId));
                }, false);
            });
        },
        /**
         * 加载bindResource
         * @param state
         * @param commit
         * @param dispatch
         * @param payload 载荷
         * @returns {*}
         */
        [constants.types.SAVE_BIND_RESOURCE]: ({state, commit, dispatch}, payload) => {

            var _this = payload['_vue'] !== null ? payload['_vue'] : this;
            return new Promise((resolve, reject) => {
                if (!payload['bindId'] || !payload['configMap']) {
                    reject(new Error('param error!'));
                    return;
                }
                var params = {
                    bindId: payload['bindId'],
                    configMapJson: JSON.stringify(payload['configMap'])
                };
                requestUtils.postSubmit(_this, constants.urls.common.bindResource.saveBindResource, params, function (data) {
                    if (data.value) {
                        state.bindMap[params.bindId] = data.value;
                    }
                    resolve(data.value);
                }, null, true);
            });
        }
    }
};
export default autoCode;
