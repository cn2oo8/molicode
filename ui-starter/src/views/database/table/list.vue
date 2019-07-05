<template>
    <div>
        <Button @click="fetchTableList" type="info" size="small">
            <Icon type="arrow-down-a"></Icon>
            获取Table列表
        </Button> &nbsp;

        <Button @click="openCreateSql" type="info" size="small">
            CREATE SQL转换
        </Button> &nbsp;

        <create-sql ref="createSql" v-on:sqlTableListNotify="sqlTableListNotify"></create-sql>

        <Row>
            <Table :columns="columns" :data="tableList" size="small" :border="true"
                   no-data-text="请确认配置并获取table列表"></Table>
        </Row>
    </div>
</template>

<script>
    import constants from '@/constants/constants';
    import requestUtils from '@/request/requestUtils.js';
    import operate from './operate';
    import createSql from './createSql';

    var _ = require('underscore')

    export default {
        data() {
            return {
                tableList: [],
                templateCheckboxList: [],
                chooserAll: true,
                columns: [
                    {
                        title: 'tableName',
                        key: 'tableName'
                    },
                    {
                        title: 'beanName',
                        key: 'id'
                    },
                    {
                        title: '中文名称',
                        key: 'cnname'
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
                                    'genCode': (value) => {
                                        this.genCode(value);
                                    },
                                    'tableModelNotify': (tableModel) => {
                                        this.tableModelNotify(tableModel);
                                    }
                                }
                            });
                        }
                    }
                ]
            };
        },
        components: {
            operate,
            createSql
        },
        methods: {
            chooserAllChange(choose) {
                if (choose) {
                    var allList = [];
                    for (var i = 0; i < this.tableList.length; i++) {
                        allList.push(this.tableList[i].desc);
                    }
                    this.templateCheckboxList = allList;
                } else {
                    this.templateCheckboxList = [];
                }
            },
            fetchTableList() {
                var projectKey = this.$store.state.autoCode.defaultProjectKey;
                if (!projectKey) {
                    this.$Message.error({
                        content: '默认项目尚未配置',
                        duration: 5
                    });
                    return;
                }
                let param = {
                    projectKey
                };
                requestUtils.postSubmit(this, constants.urls.autoCode.tableModel.getTableList, param, function (data) {
                    this.tableList = data['value'];
                }, null, true);
            },
            getCheckedTableList() {
                return _.filter(this.tableList, function (item) {
                    return item.checked;
                });
            },
            genCode(value) {
                this.$emit(constants.actions.autoCode.genCode, value);
            },
            tableModelNotify(tableModel) {
                let findTable = _.find(this.tableList, function (table) {
                    return table.tableName === tableModel.tableDefine['dbTableName'];
                });
                if (findTable) {
                    findTable.id = tableModel.tableDefine.id;
                    findTable.cnname = tableModel.tableDefine.cnname;
                }
            },
            openCreateSql() {
                this.$refs.createSql.show(true);
            },
            sqlTableListNotify: function (tableList) {
                this.tableList = tableList;
            }
        }
    }
</script>

<style scoped>

</style>
