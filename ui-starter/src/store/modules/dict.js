import Vue from 'vue';
import constants from '@/constants/constants'
import dicts from '@/constants/dicts.js'

const dict = {
    state: {
        dicts
    },
    mutations: {},
    actions: {
        [constants.types.LOAD_DICT_INFO]: ({commit}, payload) => {
            let dictData = dicts.dictData[payload['kind']]
            return new Promise((resolve, reject) => {
                if (dictData) {
                    resolve(dictData)
                } else {
                    reject(new Error('Could not load dict of kind: ' + payload['kind']));
                }
            })
        }
    }
}

export default dict;
