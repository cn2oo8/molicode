<template>
    <div>
        <Form ref="formItems" :model="formItems" :rules="formRules" :label-width="120" inline>
            <div>
                <Row>
                    <Col span="24">
                        <Form-item label="数据库Table列表" style="width: 100%">
                            <table-list ref="tableList" v-on:genCode="genCode"></table-list>
                        </Form-item>
                    </Col>
                </Row>
            </div>
        </Form>


    </div>
</template>

<script>
    import constants from '@/constants/constants';
    import requestUtils from '@/request/requestUtils.js'
    import dictRadio from '@/views/common/dict/DictRadio';
    import * as configUtil from '@/libs/configUtil.js'
    import fileChooser from '@/views/common/file/fileChooser';
    import tableList from '@/views/database/table/list'

    var _ = require('underscore')

    var validateSet = {
        tableModelDir: [{type: 'string', required: true, message: 'tableModel存放目录不能为空', trigger: 'blur'}],
        tableName: [{type: 'string', required: true, message: '表名不能为空', trigger: 'blur'}],
        cnname: [{type: 'string', required: true, message: '中文名不能为空', trigger: 'blur'}]
    };

    export default {
        name: 'tableModel',
        data() {
            return {
                formItems: {
                    tableModelDir: '',
                    tableName: '',
                    cnname: '',
                    smartFlag: '1'
                },
                constants,
                formRules: validateSet,
                disableInput: false,
                showInputMode: false, // 是否显示手动录入表名模式
                loading: false
            };
        },
        components: {
            fileChooser,
            dictRadio,
            tableList
        },

        mounted: function () {
            var configList = this.$store.state.autoCode.defaultProjectConfig;
            this.formItems.tableModelDir = configUtil.getConfigObjectVal(configList,
                constants.bizKeys.configs.pathConfig.configKey,
                constants.bizKeys.configs.pathConfig.tableModelDir);
        },
        computed: {
            storeTableModelDir: function () {
                // getter 没有名称， action 和 mutation也是
                return this.$store.getters.getConfigObjectVal(constants.bizKeys.configs.pathConfig.configKey,
                    constants.bizKeys.configs.pathConfig.tableModelDir);
            }
        },
        watch: {
            storeTableModelDir: function (newVal) {
                this.formItems.tableModelDir = newVal;
            }
        },
        methods: {
            execute() {
                if (!this.$store.state.autoCode.defaultProjectKey) {
                    this.$Message.error({
                        content: '还未设置默认项目，请先进行配置...',
                        duration: 5
                    })
                    return;
                }
                this.$refs['formItems'].validate((valid) => {
                    if (!valid) {
                        this.$Message.error({
                            content: '参数有误，请检查...',
                            duration: 5
                        })
                        return false;
                    }
                    var datas = requestUtils.serializeObject(_.clone(this.formItems), true, true);
                    datas.projectKey = this.$store.state.autoCode.defaultProjectKey;
                    this.makeTableModel(datas);
                });
            },
            makeTableModel(datas) {
                requestUtils.postSubmit(this, constants.urls.autoCode.tableModel.makeTableModel, datas, function (data) {
                    this.$Message.success({
                        content: '生成tableModel成功, tableModel=' + datas['tableName'],
                        duration: 3
                    });
                }, null, true);
            },
            batchExecute() {
                let chooseTableList = this.$refs.tableList.getCheckedTableList();
                if (_.isEmpty(chooseTableList)) {
                    this.$Message.error({
                        content: '请先拉取数据库列表，并选择相应表进行执行...',
                        duration: 5
                    })
                    return;
                }
                if (this.formItems.tableModelDir === '') {
                    this.$Message.error({
                        content: '请先选择tableModel存储目录',
                        duration: 5
                    })
                    return;
                }
                let defaultProjectKey = this.$store.state.autoCode.defaultProjectKey;
                if (!defaultProjectKey) {
                    this.$Message.error({
                        content: '还未设置默认项目，请先进行配置...',
                        duration: 5
                    })
                    return;
                }

                var _this = this;
                _.each(chooseTableList, function (item) {
                    var datas = _.clone(item);
                    datas.projectKey = defaultProjectKey;
                    datas.tableModelDir = _this.formItems.tableModelDir;
                    _this.makeTableModel(datas);
                });
            },
            genCode(value) {
                this.$emit(constants.actions.autoCode.genCode, value);
            }
        }
    };
</script>

<style scoped>

</style>
