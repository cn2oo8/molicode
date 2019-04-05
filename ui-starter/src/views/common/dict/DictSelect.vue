<template>
    <Select v-model="model" @on-change="updateValue" :clearable="clearable" :multiple="multiple" :disabled="disabled"
            :filterable="filterable" :transfer="transfer">
        <Option :value="item.itemKey" :key="item.itemKey" v-for="item in dictItems">{{ item.itemName }}</Option>
    </Select>
</template>
<script>
    import constants from '@/constants/constants'
    import {transferString, transferNumber} from '@/libs/renderUtil.js'
    var _ = require('underscore')
    export default {
        name: 'DictSelect',
        props: {
            value: {
                type: [Array, String, Number, Boolean],
                default: ''
            },
            exclusions: {
                type: [Object, Array],
                required: false
            },
            number: {
                type: Boolean,
                default: false
            },
            filterable: {
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
            clearable: {
                type: Boolean,
                default: false
            },
            disabled: {
                type: Boolean,
                default: false
            },
            multiple: {
                type: Boolean,
                default: false
            },
            transfer: {
                type: Boolean,
                default: false
            },
            labelText: {
                type: Boolean,
                default: false
            },
            localDictItems: {
                type: Array,
                default: null
            }
        },
        data() {
            return {
                model: transferString(this.value),
                dictItems: []
            }
        },
        mounted() {
            this.refresh()
        },
        watch: {
            value() {
                let newVal = transferString(this.value)
                if (_.isArray(newVal)) {
                    if (_.isArray(this.model)) {
                        if (_.difference(newVal, this.model).length !== 0) {
                            this.model = newVal
                        }
                    } else {
                        this.model = newVal
                    }
                } else {
                    if (newVal !== this.model) {
                        this.model = newVal
                    }
                }
            },
            // 添加监听exclusions
            exclusions() {
                this.refresh()
            }
        },
        methods: {
            exclusionsHandle() {
                if (this.exclusions && this.exclusions.length > 0 && this.dictItems && this.dictItems.length > 0) {
                    let cache = {}
                    // console.log(this.exclusions)
                    this.exclusions.forEach((item) => {
                        cache['val_' + item] = '1'
                    })
                    // console.log(cache)
                    let array = []
                    this.dictItems.forEach((item) => {
                        if (cache['val_' + item.itemKey] !== '1') {
                            array.push(item)
                        }
                    })
                    this.dictItems = array
                }
            },
            updateValue() {
                let v = this.model
                if (this.number) {
                    v = transferNumber(v)
                }
                this.$emit('input', v)
                if (this.labelText) {
                    let selectText = ''
                    this.dictItems.forEach((item) => {
                        if (item.k === v) {
                            selectText = item.v
                        }
                    })
                    this.$emit('on-text', {
                        value: v,
                        label: selectText
                    })
                }
            },
            refresh () {
                var _this = this;
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
                if (this.$store !== null && this.$store !== undefined) {
                    let promise = this.$store.dispatch(constants.types.LOAD_DICT_INFO, {kind: key});
                    promise.then((data) => {
                        _this.loadDict(data)
                    });
                }
            },
            loadDict (data) {
                this.dictItems = data
                this.exclusionsHandle()
            }
        }
    }
</script>
