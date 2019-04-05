<template>
  <span>
    <template v-for="(item, index) in selectedItems">
      <Tag v-if="showStyle" :color="item['cssType'] == null || item['cssType'] =='' ? 'default':item['cssType']">{{item.itemName}}</Tag>
      <span v-else>{{item.itemName}}</span>
      <span v-if="index < selectedItems.length - 1">,</span>
    </template>
  </span>
</template>
<script>
    import {toStringArray} from '@/libs/renderUtil.js'
    import constants from '@/constants/constants'
    var _ = require('underscore')

    export default {
        name: 'DictLabel',
        props: {
            value: {
                type: [String, Number, Array, Boolean],
                default: ''
            },
            kind: {
                type: String,
                default: ''
            },
            pid: {
                type: String,
                default: ''
            },
            defValue: {
                type: String,
                default: null
            },
            defDesc: {
                type: String,
                default: ''
            },
            showStyle: {
                type: Boolean,
                default: false
            },
            localDictItems: {
                type: Array,
                default: null
            }
        },
        data() {
            var itemVals = toStringArray(this.value)
            if (itemVals.length === 0) {
                if (this.defValue !== null && this.defValue !== '') {
                    itemVals.push(this.defValue)
                }
            }
            return {
                itemValues: itemVals,
                dictMap: null,
                selectedItems: []
            }
        },
        watch: {
            value() {
                this.itemValues = this.buildVals(this.value)
                if (this.dictMap !== undefined && this.dictMap !== null) {
                    this.selectedItems = toSelectedDictItem(this.itemValues, this.dictMap, this.defDesc)
                }
            },
            dictMap: function (newDictMap) {
                var _this = this
                if (!_this) {
                    return
                }
                if (newDictMap === null) {
                    this.selectedItems = []
                    return
                }
                this.selectedItems = toSelectedDictItem(_this['itemValues'], newDictMap, this.defDesc)
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
            if (this.$store !== null && this.$store !== undefined) {
                let promise = this.$store.dispatch(constants.types.LOAD_DICT_INFO, {kind: key});
                promise.then((data) => {
                    _this.loadDict(data)
                });
            }
        },
        methods: {
            buildVals(val) {
                let vals = toStringArray(val)
                if (vals.length === 0) {
                    if (this.defValue != null && this.defValue !== '') {
                        vals.push(this.defValue)
                    }
                }
                return vals
            },
            loadDict: function (dictList) {
                let obj = {}
                dictList.forEach((item) => {
                    obj[item.itemKey] = item
                })
                this.dictMap = obj
            }
        }
    }

    function toSelectedDictItem(values, dictMap, defDesc) {
        var selectDictItems = []
        _.each(values, function (item) {
            var dict = dictMap[item]
            if (dict) {
                selectDictItems.push(dict)
            } else {
                if (defDesc) {
                    selectDictItems.push({itemKey: item, itemName: defDesc})
                }
            }
        })
        return selectDictItems
    }
</script>
