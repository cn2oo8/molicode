<template>
  <Row>
    <Col span="11" style="padding-right: 5px;">
    <Select style="width: 100%" v-model="parent" :clearable="clearable" @on-change="(updateChildList() && updateValue())"  :disabled="disabled">
      <Option :value="item.k" :key="item.k" v-for="item in parentDictItems">{{ item.v }}</Option>
    </Select>
    </Col>
    <Col span="13" style="padding-right: 5px;">
    <Select style="width: 100%" v-model="child" :clearable="clearable" @on-change="updateValue"  :disabled="disabled">
      <Option :value="item.k" :key="item.k" v-for="item in childDictItems">{{ item.v }}</Option>
    </Select>
    </Col>
  </Row>
</template>
<script>
  // import cfs from '@/utils/configs.js'
  export default {
    name: 'DictCascadeSelect',
    props: {
      value: {
        type: [Array],
        default: ['', '']
      },
      parentKind: {
        type: String,
        required: true
      },
      clearable: {
        type: Boolean,
        default: true
      },
      disabled: {
        type: Boolean,
        default: false
      },
      number: {
        type: Boolean,
        default: false
      }
    },
    data () {
      return {
        parent: this.value[0].toString(),
        child: this.value[1].toString(),
        parentDictItems: [],
        childDictItems: []
      }
    },
    mounted () {
      if (this.parentKind === null || this.parentKind === '') {
        return
      }
      this.queryDictItems(this.parentKind, true)
    },
    watch: {
      value (val, oldVal) {
        if (val && val.length === 2) {
          this.parent = (val[0] === null ? '' : val[0].toString())
          this.child = (val[1] === null ? '' : val[1].toString())
        }
      },
      model () {
        this.$emit('input', [this.parent, this.child])
      }
    },
    methods: {
      queryDictItems (key, parent, pid) {
        let storeKey
        if (parent) {
          storeKey = key
        } else {
          storeKey = parent + '_' + pid
        }
        let items = this.$store.state.dicts[storeKey]
        if (items == null || items.list == null) {
          this.$nextTick(function () {
            this.$ajax.get('/api/organization/dict/getDictItems.do', {params: {kind: key, pid: pid}}).then(response => {
              this.$store.commit('putDictItems', {kind: storeKey, items: response.data})
              if (parent) {
                this.parentDictItems = response.data
              } else {
                this.childDictItems = response.data
              }
            })
          })
        } else {
          if (parent) {
            this.parentDictItems = items.list
          } else {
            this.childDictItems = items.list
          }
        }
      },
      updateChildList () {
        if (this.parent) {
          this.queryDictItems(this.parentKind, false, this.parent)
        } else {
          this.childDictItems = []
          this.child = ''
        }
        return true
      },
      updateValue () {
        this.$emit('input', [this.parent, this.child])
        this.$emit('on-change', [this.parent, this.child])
      }
    }
  }
</script>
