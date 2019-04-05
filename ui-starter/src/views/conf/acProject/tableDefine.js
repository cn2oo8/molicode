/**
 * 抽取出来方便
 */
import * as renderUtil from '@/libs/renderUtil.js'
import constants from '@/constants/constants'
import operate from './operate.vue';
import dictLabel from '@/views/common/dict/DictLabel'
import dictSelect from '@/views/common/dict/DictSelect'
import dictCheckbox from '@/views/common/dict/DictCheckBox'

const searchFormRules = {
    name: [{type: 'string', required: false, message: '名称不能为空', trigger: 'blur'}]
};

let tableDefine = {
    data() {
        return {
            loading: false,
            columns: [

                {
                    title: 'id',
                    key: 'id'
                },

                {
                    title: '名称',
                    key: 'name'
                },
                {
                    title: '项目key',
                    key: 'projectKey'
                },

                {
                    title: '类型',
                    render: (h, params) => {
                        return h(dictLabel, {
                            props: {
                                value: params.row['type'],
                                kind: constants.dicts.dictKinds.PROJECT_TYPE_DICT,
                                showStyle: true
                            }
                        })
                    }
                },
                {
                    title: '创建人',
                    key: 'creator'
                },
                {
                    title: '状态',
                    render: (h, params) => {
                        return h(dictLabel, {
                            props: {
                                value: params.row['status'],
                                kind: constants.dicts.dictKinds.STD_STATUS,
                                showStyle: true
                            }
                        })
                    }
                },
                {
                    title: '修改时间',
                    key: 'modified',
                    render: (h, params) => {
                        return h('div', renderUtil.formatDateTime(params.row.modified))
                    }
                },

                {
                    title: '操作',
                    key: 'action',
                    width: 250,
                    align: 'center',
                    render: (h, params) => {
                        return h(operate, {
                            props: {
                                item: params.row
                            },
                            on: {
                                'refreshList': () => {
                                    this.loadData()
                                },
                                'updateItem': (item) => {
                                    this.editItem(item)
                                }
                            }
                        });
                    }
                }
            ],
            queryResult: {
                dataList: [],
                pageQuery: {
                    totalCount: 0,
                    pageCount: 0,
                    pageNo: 1,
                    pageSize: 10
                }
            },
            formSearch: {
                'id': '',
                'name': '',
                'type': 1,
                'status': 1

            },
            searchFormRules: searchFormRules,
            constants
        }
    },
    mounted() {
        this.loadData()
    },
    components: {
        operate,
        dictLabel,
        dictSelect,
        dictCheckbox
    }
}
export default tableDefine;