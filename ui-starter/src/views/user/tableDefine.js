/**
 * 抽取出来方便
 */
import * as renderUtil from '@/libs/renderUtil.js';
import constants from '@/constants/constants';
import operate from './operate.vue';
import dictLabel from '@/views/common/dict/DictLabel';
import dictSelect from '@/views/common/dict/DictSelect';
import dictCheckbox from '@/views/common/dict/DictCheckBox';

const searchFormRules = {
    id: [{type: 'integer', required: false, message: 'Id不能为空', trigger: 'blur'}],
    userName: [{type: 'string', required: false, message: '用户名称不能为空', trigger: 'blur'}],
    nickName: [{type: 'string', required: false, message: '用户昵称不能为空', trigger: 'blur'}],
    roleCode: [{type: 'string', required: false, message: '角色码不能为空', trigger: 'blur'}]
};

let tableDefine = {
    data () {
        return {
            loading: false,
            columns: [

                {
                    title: 'Id',
                    key: 'id',
                    width: 100
                },

                {
                    title: '用户名称',
                    key: 'userName',
                    width: 100
                },

                {
                    title: '用户昵称',
                    key: 'nickName',
                    width: 100
                },

                {
                    title: '性别',
                    width: 100,
                    render: (h, params) => {
                        return h(dictLabel, {
                            props: {
                                value: params.row['gender'],
                                kind: constants.dicts.dictKinds.STD_GENDER,
                                showStyle: true
                            }
                        });
                    }
                },
                {
                    title: '出生日期',
                    key: 'birthDay',
                    width: 100,
                    render: (h, params) => {
                        return h('div', renderUtil.formatDate(params.row.birthDay));
                    }
                },

                {
                    title: '用户标签',
                    key: 'userMark',
                    width: 100
                },

                {
                    title: '角色码',
                    key: 'roleCode',
                    width: 100
                },

                {
                    title: '备注',
                    key: 'remark',
                    width: 100
                },

                {
                    title: '创建人',
                    key: 'creator',
                    width: 100
                },

                {
                    title: '操作人',
                    key: 'operator',
                    width: 100
                },

                {
                    title: '并发版本号',
                    key: 'concurrentVersion',
                    width: 100
                },

                {
                    title: '数据版本号',
                    key: 'dataVersion',
                    width: 100
                },

                {
                    title: '状态',
                    width: 100,
                    render: (h, params) => {
                        return h(dictLabel, {
                            props: {
                                value: params.row['status'],
                                kind: constants.dicts.dictKinds.STD_STATUS,
                                showStyle: true
                            }
                        });
                    }
                },

                {
                    title: '创建时间',
                    key: 'created',
                    width: 100,
                    render: (h, params) => {
                        return h('div', renderUtil.formatDateTime(params.row.created));
                    }
                },

                {
                    title: '修改时间',
                    key: 'modified',
                    width: 100,
                    render: (h, params) => {
                        return h('div', renderUtil.formatDateTime(params.row.modified));
                    }
                },

                {
                    title: '操作',
                    key: 'action',
                    width: 150,
                    fixed: 'right',
                    align: 'center',
                    render: (h, params) => {
                        return h(operate, {
                            props: {
                                item: params.row
                            },
                            on: {
                                'refreshList': () => {
                                    this.loadData();
                                },
                                'updateItem': (item) => {
                                    this.editItem(item);
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
                'id': null,
                'userName': '',
                'nickName': '',
                'roleCode': '',
                'status': 1

            },
            searchFormRules: searchFormRules,
            constants
        };
    },
    mounted () {
        this.loadData();
    },
    components: {
        operate,
        dictLabel,
        dictSelect,
        dictCheckbox
    }
};
export default tableDefine;
