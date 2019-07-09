import constants from '@/constants/constants';
import requestUtils from '@/request/requestUtils.js';
import * as configUtil from '@/libs/configUtil.js'

var _ = require('underscore')


function removeDictElement(dictKind, removeElements) {
    let dictList = constants.dicts.dictData[dictKind];
    if (dictList === null || dictList === undefined) {
        return;
    }
    let newDictList = [];
    for (var index = 0; index < dictList.length; index++) {
        var element = dictList[index];
        if (_.contains(removeElements, element['itemKey'])) {
            continue;
        }
        newDictList.push(element);
    }
    constants.dicts.dictData[dictKind] = newDictList;
}

const autoCode = {
    state: {
        defaultProjectKey: null,
        defaultProject: null,
        defaultProjectConfig: [],
        globalConfig: {
            mavenConfig: null
        },
        autoMake: null,
        profile: {}
    },
    mutations: {
        setDefaultProjectKey(state, projectKey) {
            state.defaultProjectKey = projectKey;
        },
        setDefaultProject(state, project) {
            state.defaultProject = project;
            if (project) {
                state.defaultProjectKey = project.projectKey;
            }
        },
        setDefaultProjectConfig(state, configList) {
            state.defaultProjectConfig = configList;
        },
        setGlobalConfig(state, commonExtInfo) {
            if (commonExtInfo && commonExtInfo['extValue']) {
                state.globalConfig[commonExtInfo['extKey']] = JSON.parse(commonExtInfo['extValue']);
            }
        },
        setSystemProfile(state, profile) {
            state.profile = profile;
            if (state.profile['browserWindowName'] === 'headless' || state.profile['browserWindowName'] === 'server') {
                removeDictElement(constants.dicts.dictKinds.TEMPLATE_TYPE_DICT, ['local']);
                removeDictElement(constants.dicts.dictKinds.RESOURCE_TYPE_DICT, ['file']);
                removeDictElement(constants.dicts.dictKinds.OUTPUT_TYPE_DICT, ['1']);
            }
        }
    },
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
         * 加载默认的项目信息
         * @param state
         * @param commit
         * @param dispatch
         * @param payload 载荷
         * @returns {*}
         */
        [constants.types.LOAD_DEFAULT_PROJECT]: ({state, commit, dispatch}, payload) => {
            var searchParam = {
                ownerType: 3,
                ownerCode: 'admin',
                extKey: 'defaultProjectKey'
            };
            var _this = payload['_vue'] ? payload['_vue'] : this;
            if (state.defaultProjectKey) {
                return dispatch(constants.types.LOAD_DEFAULT_PROJECT_BY_PRJ_KEY, {loadConfig: payload['loadConfig']});
            }
            return new Promise((resolve, reject) => {
                requestUtils.postSubmit(_this, constants.urls.conf.commonExtInfo.getByOwnerAndKey, searchParam, function (data) {
                    if (data.value && data.value.extValue) {
                        commit(constants.types.SET_DEFAULT_PROJECT_KEY, data.value.extValue)
                        var innerPromise = dispatch(constants.types.LOAD_DEFAULT_PROJECT_BY_PRJ_KEY, {loadConfig: payload['loadConfig']});
                        innerPromise.then(function (data) {
                            resolve(data);
                        }).catch(function (data) {
                            reject(data);
                        });
                    } else {
                        resolve(null);
                    }
                }, function (data) {
                    reject(new Error('Could not default project, getKey'));
                }, false);
            });
        },
        /**
         * 加载项目下的配置信息，通过项目key
         * @param state
         * @param commit
         * @param dispatch
         * @param payload
         * @returns {Promise<any>}
         */
        [constants.types.LOAD_DEFAULT_PROJECT_BY_PRJ_KEY]: ({state, commit, dispatch}, payload) => {
            var projectKey = payload['projectKey'] ? payload['projectKey'] : state.defaultProjectKey;

            if (payload['loadConfig']) {
                dispatch(constants.types.LOAD_DEFAULT_PROJECT_CONFIG, {});
            }
            return new Promise((resolve, reject) => {
                if (projectKey) {
                    var searchParam = {projectKey: projectKey};
                    requestUtils.postSubmit(this, constants.urls.conf.acProject.getByProjectKey, searchParam, function (data) {
                        commit(constants.types.SET_DEFAULT_PROJECT, data.value)
                        resolve(data.value);
                    }, function (data) {
                        reject(new Error('Could not get default project, getProject by key:' + projectKey));
                    }, false);
                } else {
                    reject(new Error('Could not get default project, project key is null'));
                }
            });
        },
        /**
         * 保存默认项目key
         * @param state
         * @param commit
         * @param payload
         * @returns {Promise<any>}
         */
        [constants.types.SAVE_DEFAULT_PROJECT_EXT]: ({state, commit}, payload) => {
            var params = {
                ownerType: 3,
                ownerCode: 'admin',
                extKey: 'defaultProjectKey',
                extValue: payload.projectKey
            };
            var _this = payload['_vue'] ? payload['_vue'] : this;
            return new Promise((resolve, reject) => {
                requestUtils.postSubmit(_this, constants.urls.conf.commonExtInfo.save, params, function (data) {
                    if (payload['project']) {
                        commit(constants.types.SET_DEFAULT_PROJECT, payload['project']);
                    }
                    resolve(data);
                });
            });
        },
        /**
         * 加载默认项目的配置列表
         * @param state
         * @param commit
         * @param payload
         * @returns {Promise<any>}
         */
        [constants.types.LOAD_DEFAULT_PROJECT_CONFIG]: ({state, commit}, payload) => {
            let projectKey = payload['project'] ? payload.project.projectKey : null;
            if (!projectKey) {
                projectKey = state.defaultProjectKey;
            }
            var searchParam = {
                projectKey: projectKey,
                status: 1,
                pageSize: 100
            };
            var _this = payload['_vue'] ? payload['_vue'] : this;
            return new Promise((resolve, reject) => {
                requestUtils.postSubmit(_this, constants.urls.conf.acConfig.list, searchParam, function (data) {
                    commit(constants.types.SET_DEFAULT_PROJECT_CONFIG, data.value);
                    resolve(data.value);
                }, null, false);
            });
        },
        /**
         * 复制项目并设置为默认项目
         * @param state
         * @param commit
         * @param payload
         * @returns {Promise<any>}
         */
        [constants.types.COPY_PROJECT]: ({state, commit}, payload) => {
            let project = payload['project'];
            var _this = payload['_vue'] ? payload['_vue'] : this;
            return new Promise((resolve, reject) => {
                if (!project) {
                    resolve(project);
                    return;
                }
                requestUtils.postSubmit(_this, constants.urls.conf.acProject.copyProject, project, function (data) {
                    // commit(constants.types.SET_DEFAULT_PROJECT_CONFIG, data.value);
                    if (payload['setDefault'] === '1') {
                        this.$store.dispatch(constants.types.SAVE_DEFAULT_PROJECT_EXT, {
                            projectKey: data.value.projectKey,
                            project: data.value
                        });
                    }

                    resolve(data.value);
                }, null, false);
            });
        },
        [constants.types.SAVE_GLOBAL_CONFIG]: ({state, commit}, payload) => {
            var params = {
                ownerType: 1,
                ownerCode: 'admin',
                extKey: payload['extKey'],
                extValue: payload['extValue'],
                type: payload['type'] ? payload['type'] : 2
            };
            var _this = payload['_vue'] ? payload['_vue'] : this;
            return new Promise((resolve, reject) => {
                requestUtils.postSubmit(_this, constants.urls.conf.commonExtInfo.save, params, function (data) {
                    commit(constants.types.SET_GLOBAL_CONFIG, data.value);
                    resolve(data.value);
                }, null, false);
            });
        },
        /**
         * 加载全局配置信息
         * @param state
         * @param commit
         * @param dispatch
         * @param payload 载荷
         * @returns {*}
         */
        [constants.types.LOAD_GLOBAL_CONFIG]: ({state, commit, dispatch}, payload) => {
            var searchParam = {
                ownerType: 3,
                ownerCode: 'admin',
                extKey: payload['extKey']
            };
            var _this = payload['_vue'] ? payload['_vue'] : this;
            return new Promise((resolve, reject) => {
                requestUtils.postSubmit(_this, constants.urls.conf.commonExtInfo.getByOwnerAndKey, searchParam, function (data) {
                    if (data.value) {
                        commit(constants.types.SET_GLOBAL_CONFIG, data.value);
                        resolve(data.value);
                    } else {
                        resolve(null);
                    }
                }, function (data) {
                    reject(new Error('Could not load global config ,key =' + payload['extKey']));
                }, false);
            });
        },
        /**
         * 获取默认项目的autoMake信息
         * @param state
         * @param commit
         * @param dispatch
         * @param payload 载荷
         * @returns {*}
         */
        [constants.types.LOAD_AUTOMAKE_DEF_PRJ]: ({state, commit, dispatch}, payload) => {
            var projectKey = payload['projectKey'] ? payload['projectKey'] : state.defaultProjectKey;
            var configList = state.defaultProjectConfig;
            var configObject = configUtil.getConfigObject(configList, constants.bizKeys.configs.pathConfig.configKey);
            var searchParam = {
                autoXmlPath: configObject[constants.bizKeys.configs.pathConfig.autoXmlPath],
                templateBaseDir: configObject[constants.bizKeys.configs.pathConfig.templateBaseDir],
                projectKey: projectKey,
                flushMaven: payload['flushMaven'],
                sid: payload['sid']
            }
            var _this = payload['_vue'] ? payload['_vue'] : this;
            return new Promise((resolve, reject) => {
                requestUtils.postSubmit(_this, constants.urls.autoCode.coder.getAutoMake, searchParam, function (data) {
                    var autoMake = data['autoMake'];
                    state.autoMake = autoMake;
                    resolve(autoMake);
                }, function (data) {
                    reject(data);
                }, true);
            });
        },
        /**
         * 获取系统profile信息
         * @param state
         * @param commit
         * @param dispatch
         * @param payload 载荷
         * @returns {*}
         */
        [constants.types.LOAD_SYSTEM_PROFILE]: ({state, commit, dispatch}, payload) => {
            var _this = payload['_vue'] ? payload['_vue'] : this;
            return new Promise((resolve, reject) => {
                if (state.profile['browserWindowName']) {
                    resolve(state.profile);
                    return;
                }
                requestUtils.postSubmit(_this, constants.urls.sys.system.getProfileInfo, {}, function (data) {
                    var profile = data['value'];
                    commit('setSystemProfile', profile)
                    resolve(profile);
                }, function (data) {
                    reject(data);
                }, true);
            });
        }
    }
};
export default autoCode;
