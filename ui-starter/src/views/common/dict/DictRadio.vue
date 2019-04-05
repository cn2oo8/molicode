<template>
  <span>
     <RadioGroup v-model="radioModel" @on-change="updateValue" :size="size" :type="type">
        <Radio v-for="item in dictItems" :label="item.itemName" :key="item.itemKey" :disabled="disabled"></Radio>
    </RadioGroup>
  </span>
</template>
<script>
    import {transferString, transferNumber} from '@/libs/renderUtil.js'
    import constants from '@/constants/constants'

    var _ = require('underscore')

    export default {
        name: 'DictRadio',
        props: {
            value: {
                type: [String, Number],
                default: ''
            },
            number: {
                type: Boolean,
                default: false
            },
            kind: {
                type: String,
                default: ''
            },
            pid: {
                type: String,
                default: ''
            },
            disabled: {
                type: Boolean,
                default: false
            },
            transfer: {
                type: Boolean,
                default: false
            },
            size: {
                type: String,
                default: 'default'
            },
            type: {
                type: String,
                default: null
            },
            localDictItems: {
                type: Array,
                default: null
            }
        },
        data() {
            var model = transferString(this.value);
            return {
                radioModel: null,
                model: model,
                dictItems: []
            }
        },
        mounted () {
            let _this = this;
            if (this.localDictItems !== null && this.localDictItems.length > 0) {
                _this.loadDict(this.localDictItems)
                return
            }
            if (this.kind === null || this.kind === '') {
                return
            }
            let key = this.kind
            if (this.pid !== '') {
                key = this.kind + '_' + this.pid
            }

            if (this.$store) {
                let promise = this.$store.dispatch(constants.types.LOAD_DICT_INFO, {kind: key});
                promise.then((data) => {
                    _this.loadDict(data)
                });
            }
        },
        watch: {
            value(newVal) {
                newVal = transferString(newVal)
                let name = this.transToName(newVal);
                if (name === null || this.radioModel === name) {
                    return;
                }
                this.radioModel = name;
            },
            radioModel: function (newVal) {
                let transKey = this.transToKey(newVal)
                if (transKey === null || this.model === transKey) {
                    return;
                }
                this.model = transKey;
                this.updateValue();
            }
        },
        methods: {
            updateValue () {
                let v = this.model
                if (this.number) {
                    v = transferNumber(v)
                }
                this.$emit('input', v)
            },
            loadDict(data) {
                this.dictItems = data;
                let name = this.transToName(this.model);
                if (name === null || this.radioModel === name) {
                    return;
                }
                this.radioModel = name;
            },
            transToName(key) {
                let dictItem = _.find(this.dictItems, function (item) {
                    return item.itemKey === key;
                });
                return dictItem ? dictItem.itemName : null;
            },
            transToKey(name) {
                let dictItem = _.find(this.dictItems, function (item) {
                    return item.itemName === name;
                });
                return dictItem ? dictItem.itemKey : null;
            }
        }
    }
</script>
