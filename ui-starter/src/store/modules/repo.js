import constants from '@/constants/constants';
import requestUtils from '@/request/requestUtils.js';

const dict = {
    state: {
        'baseProjectRepo': null,
        'templateRepo': null
    },
    mutations: {},
    actions: {
        [constants.types.LOAD_REPO_BASE_PROJECT]: ({state, commit, rootState}, payload) => {
            var _this = payload['_vue'] ? payload['_vue'] : this;
            return new Promise((resolve, reject) => {
                if (state.baseProjectRepo !== null && !payload['refresh']) {
                    resolve(state.baseProjectRepo);
                    return;
                }

                let profile = rootState.autoCode['profile'];
                let reqUrl = constants.urls.repo.github.baseProjectRepos;
                if (profile && profile['baseProjectReposUrl'] && profile['baseProjectReposUrl'] !== '') {
                    reqUrl = profile['baseProjectReposUrl'];
                }
                requestUtils.getSubmit(_this, reqUrl, {}, function (data) {
                    state.baseProjectRepo = data;
                    resolve(data);
                }, function (data) {
                    reject(data);
                }, true);
            });
        },
        [constants.types.LOAD_REPO_TEMPLATE]: ({state, commit, rootState}, payload) => {
            var _this = payload['_vue'] ? payload['_vue'] : this;
            return new Promise((resolve, reject) => {
                if (state.templateRepo !== null && !payload['refresh']) {
                    resolve(state.templateRepo);
                    return;
                }
                let profile = rootState.autoCode['profile'];
                let reqUrl = constants.urls.repo.github.templateRepos;
                if (profile && profile['templateReposUrl'] && profile['templateReposUrl'] !== '') {
                    reqUrl = profile['templateReposUrl'];
                }
                requestUtils.getSubmit(_this, reqUrl, {}, function (data) {
                    state.templateRepo = data;
                    resolve(data);
                }, function (data) {
                    reject(data);
                }, true);
            });
        }
    }
}

export default dict;
