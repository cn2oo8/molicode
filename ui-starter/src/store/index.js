import Vue from 'vue';
import Vuex from 'vuex';

import app from './modules/app';
import user from './modules/user';
import dict from './modules/dict';
import autoCode from './modules/autoCode';
import config from './modules/config';
import repo from './modules/repo';

Vue.use(Vuex);

const store = new Vuex.Store({
    modules: {
        app,
        user,
        dict,
        autoCode,
        config,
        repo
    }
});

export default store;
