<template>
    <Modal v-model="showModal"
           :title="title"
           ok-text="> 执行代码生成"
           @on-cancel="cancel"
           width="94%">


        <Form :label-width="120" inline>
            <Row>
                <Col span="8">
                    <Form-item label="className(类名)" prop="id" style="width: 90%">
                        <Input v-model="tableDefine.id"></Input>
                    </Form-item>
                </Col>

                <Col span="8">
                    <Form-item label="中文名称" prop="cnname" style="width: 90%">
                        <Input v-model="tableDefine.cnname"></Input>
                    </Form-item>
                </Col>
                <Col span="8">
                    <Form-item label="表自定义扩展" prop="customProps" style="width: 90%">
                        <custom-props :customPropsObject="tableModel['customProps']"
                                      @on-change="tableCustomPropsChange" v-if="showTableCusProps"></custom-props>
                    </Form-item>
                </Col>
            </Row>

        </Form>

        <Table :columns="columns" :data="tableDefine.columns" size="small" :border="true"></Table>

        <div slot="footer">
            <Button type="default" :loading="loading" @click="cancel">
                取消
            </Button>

            <Button type="info" :loading="loading" @click="saveModelAndGenCode(false)">
                <Icon type="checkmark"></Icon>
                保存表模型
            </Button>
            <Button type="primary" :loading="loading" @click="saveModelAndGenCode(true)">
                <Icon type="play"></Icon>
                执行代码生成
            </Button>
        </div>
    </Modal>
</template>

<script>
    import constants from '@/constants/constants';
    import requestUtils from '@/request/requestUtils.js';
    import dictSelect from '@/views/common/dict/DictSelect'
    import customProps from './customProps'

    var _ = require('underscore')

    export default {
        props: {
            item: {
                type: Object,
                required: true
            }
        },
        data() {
            return {
                loading: false,
                loaded: false,
                showModal: false,
                showTableCusProps: false,
                title: '编辑并生成代码',
                tableModel: {},
                tableDefine: {
                    columns: []
                },
                columns: [
                    {
                        title: '列名',
                        key: 'columnName',
                        width: 120
                    },
                    {
                        title: '注释',
                        key: 'comment',
                        width: 150
                    },
                    {
                        title: '类型',
                        key: 'columnType',
                        width: 100
                    },
                    {
                        title: '最大长度',
                        key: 'length',
                        width: 80
                    },
                    {
                        title: '是否主键',
                        key: 'isPK',
                        width: 80
                    },
                    {
                        title: '显示名称',
                        key: 'cnname',
                        width: 170,
                        render: (h, params) => {
                            return h('i-input', {
                                props: {
                                    value: params.row['cnname'],
                                    size: 'small'
                                },
                                on: {
                                    'on-blur': (event) => {
                                        this.tableDefine.columns[params.index]['cnname'] = event.target.value;
                                    }
                                }
                            });
                        }
                    },
                    {
                        title: '控件',
                        key: 'jspTag',
                        width: 160,
                        render: (h, params) => {
                            return h(dictSelect, {
                                props: {
                                    value: params.row['jspTag'],
                                    kind: constants.dicts.dictKinds.STD_JSP_TAG,
                                    size: 'small'
                                }
                            });
                        }
                    },
                    {
                        title: '必填',
                        key: 'canBeNull',
                        width: 55,
                        render: (h, params) => {
                            return h('i-switch', {
                                props: {
                                    value: params.row['canBeNull'] === false,
                                    size: 'small'
                                },
                                on: {
                                    'on-change': (val) => {
                                        this.tableDefine.columns[params.index]['canBeNull'] = !val;
                                    }
                                }
                            });
                        }
                    },
                    {
                        title: '条件',
                        key: 'in_searchKey',
                        width: 55,
                        render: (h, params) => {
                            return h('i-switch', {
                                props: {
                                    value: params.row['in_searchKey'],
                                    size: 'small'
                                },
                                on: {
                                    'on-change': (val) => {
                                        this.tableDefine.columns[params.index]['in_searchKey'] = val;
                                    }
                                }
                            });
                        }
                    },
                    {
                        title: '列表',
                        key: 'in_queryList',
                        width: 55,
                        render: (h, params) => {
                            return h('i-switch', {
                                props: {
                                    value: params.row['in_queryList'],
                                    size: 'small'
                                },
                                on: {
                                    'on-change': (val) => {
                                        this.tableDefine.columns[params.index]['in_queryList'] = val;
                                    }
                                }
                            });
                        }
                    },
                    {
                        title: '插入',
                        key: 'in_addList',
                        width: 55,
                        render: (h, params) => {
                            return h('i-switch', {
                                props: {
                                    value: params.row['in_addList'],
                                    size: 'small'
                                },
                                on: {
                                    'on-change': (val) => {
                                        this.tableDefine.columns[params.index]['in_addList'] = val;
                                    }
                                }
                            });
                        }
                    },
                    {
                        title: '更新',
                        key: 'in_updateList',
                        width: 55,
                        render: (h, params) => {
                            return h('i-switch', {
                                props: {
                                    value: params.row['in_updateList'],
                                    size: 'small'
                                },
                                on: {
                                    'on-change': (val) => {
                                        this.tableDefine.columns[params.index]['in_updateList'] = val;
                                    }
                                }
                            });
                        }
                    },
                    {
                        title: '查看',
                        key: 'in_viewList',
                        width: 55,
                        render: (h, params) => {
                            return h('i-switch', {
                                props: {
                                    value: params.row['in_viewList'],
                                    size: 'small'
                                },
                                on: {
                                    'on-change': (val) => {
                                        this.tableDefine.columns[params.index]['in_viewList'] = val;
                                    }
                                }
                            });
                        }
                    },
                    {
                        title: 'customProps',
                        width: 120,
                        render: (h, params) => {
                            return h(customProps, {
                                props: {
                                    customPropsObject: params.row['customProps'],
                                    size: 'small'
                                },
                                on: {
                                    'on-change': (val) => {
                                        this.tableDefine.columns[params.index]['customProps'] = val;
                                    }
                                }
                            });
                        }
                    }
                ]
            };
        },
        methods: {
            cancel() {
                this.showModal = false;
            },
            'show': function (isShow) {
                this.showModal = isShow;
                if (isShow && !this.loaded) {
                    this.fetchTableModel();
                    this.showTableCusProps = true;
                }
            },
            fetchTableModel() {
                var projectKey = this.$store.state.autoCode.defaultProjectKey;
                if (!projectKey) {
                    this.$Message.error({
                        content: '默认项目尚未配置',
                        duration: 5
                    });
                    return;
                }
                let param = {
                    projectKey,
                    tableName: this.item.tableName,
                    'tableSourceName': this.item['sourceName']
                };
                requestUtils.postSubmit(this, constants.urls.autoCode.tableModel.getTableInfo, param, function (data) {
                    processData(data['value'])
                    this.tableModel = data['value'];
                    this.tableDefine = this.tableModel.tableDefine;
                    this.title = '编辑生成代码，表（' + this.tableModel.tableDefine.dbTableName + ')';
                }, null, true);
            },
            saveModelAndGenCode: function (genCode) {
                processBeforeSave(this.tableModel);
                let tableModelJson = JSON.stringify(this.tableModel);
                var projectKey = this.$store.state.autoCode.defaultProjectKey;
                var param = {
                    projectKey,
                    tableModelJson,
                    tableName: this.tableDefine.dbTableName
                }
                requestUtils.postSubmit(this, constants.urls.autoCode.tableModel.saveTableModel, param, function (data) {
                    if (genCode) {
                        this.$emit(constants.actions.autoCode.genCode, data['value']);
                        this.show(false);
                    } else {
                        this.$Message.success({
                            content: '保存表模型设计成功',
                            duration: 5
                        });
                        this.$emit(constants.actions.autoCode.tableModelNotify, this.tableModel);
                    }
                }, null, true);
            },
            tableCustomPropsChange: function (val) {
                this.tableModel['customProps'] = val;
            }
        },
        components: {
            dictSelect,
            customProps
        }
    };

    const inFieldKeyList = ['searchKey', 'queryList', 'addList', 'updateList', 'viewList']

    function processData(tableModel) {
        let tableDefine = tableModel.tableDefine;
        _.each(inFieldKeyList, function (key) {
            let value = tableModel['bizFieldsMap'][key];
            if (value === null || value === undefined) {
                value = '';
            }
            let fieldsList = value.split(',');
            let inKey = 'in_' + key;
            _.each(tableDefine.columns, function (column) {
                column[inKey] = _.contains(fieldsList, column['columnName']);
            });
        });
    }


    function processBeforeSave(tableModel) {
        let tableDefine = tableModel.tableDefine;
        _.each(inFieldKeyList, function (key) {
            let inKey = 'in_' + key;
            let bizKeyList = [];
            _.each(tableDefine.columns, function (column) {
                if (column[inKey]) {
                    bizKeyList.push(column['columnName']);
                }
            });
            if (bizKeyList.length > 0) {
                tableModel['bizFieldsMap'][key] = bizKeyList.join(',');
            }
        });
    }
</script>
