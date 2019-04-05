<template>
    <div>
        <Form ref="formItems" :model="formItems" :rules="formRules" :label-width="120" inline>

            <Row>
                <Col span="3" style="text-align: center">
                    说明：
                </Col>
                <Col span="20" style="font-size: smaller">
                    使用数据库表生成代码时候，需要进行配置，目前仅支持MySQL数据库，其它数据库后期进行支持,如果有需要请联系。
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="数据库名称" prop="databaseName" style="width: 80%">
                        <dict-select v-model="formItems.databaseName"
                                     :kind="this.constants.dicts.dictKinds.DATABASE_NAME_DICT"
                                     :clearable="false"></dict-select>
                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="驱动名称" prop="driverClass" style="width: 80%">
                        <Input v-model="formItems.driverClass" :maxlength="50" :disabled="disableInput"></Input>
                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="链接地址" prop="url" style="width: 80%">
                        <Input v-model="formItems.url" :maxlength="200" :disabled="disableInput"></Input>
                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="用户名" prop="username" style="width: 80%">
                        <Input v-model="formItems.username" :maxlength="50" :disabled="disableInput"></Input>
                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="密码" prop="password" style="width: 80%">
                        <Input v-model="formItems.password" :maxlength="50" :disabled="disableInput"></Input>
                    </Form-item>
                </Col>
            </Row>

            <FormItem>
                <Button type="primary" @click="save" :loading="loading">
                    <Icon type="android-done"></Icon>
                    Save
                </Button>
            </FormItem>
        </Form>

    </div>
</template>

<script>
    import constants from '@/constants/constants';
    import dictSelect from '@/views/common/dict/DictSelect'
    import requestUtils from '@/request/requestUtils.js'

    var _ = require('underscore')

    var validateSet = {
        driverClass: [{type: 'string', required: true, message: '驱动名称不能为空', trigger: 'blur'}],
        url: [{type: 'string', required: true, message: '数据库链接不能为空', trigger: 'blur'}],
        username: [{type: 'string', required: true, message: '用户名不能为空', trigger: 'blur'}],
        password: [{type: 'string', required: true, message: '密码不能为空', trigger: 'blur'}]
    };

    var defConfig = {
        databaseName: 'mysql',
        driverClass: 'com.mysql.jdbc.Driver',
        url: 'jdbc:mysql://127.0.0.1:3306/auto-code?useUnicode=true&characterEncoding=UTF8',
        username: 'root',
        password: ''
    };

    export default {
        props: {
            defaultProjectKey: {
                type: String
            },
            configKey: {
                type: String,
                default: 'databaseConfig'
            },
            configInfo: {
                type: Object,
                default: function () {
                    return defConfig;
                }
            }
        },
        data: function () {
            return {
                projectKey: this.defaultProjectKey,
                formItems: _.clone(this.configInfo),
                formRules: validateSet,
                constants,
                disableInput: false,
                loading: false
            };
        },
        methods: {
            save: function () {
                if (!this.projectKey) {
                    this.$Message.error({
                        content: '您还没有选择项目',
                        duration: 3
                    });
                    return;
                }
                this.$refs['formItems'].validate((valid) => {
                    if (!valid) {
                        return false;
                    }
                    requestUtils.postSubmit(this, constants.urls.conf.acConfig.save, this.getConfigData(), function (data) {
                        this.$Message.success({
                            content: '保存数据库配置成功',
                            duration: 3
                        });
                    }, null, true);
                });
            },
            getConfigData () {
                return {
                    projectKey: this.projectKey,
                    configKey: this.configKey,
                    configValue: JSON.stringify(this.formItems),
                    type: 2,
                    scope: 1
                };
            },
            notifyConfigInfo(projectKey, configInfo) {
                this.projectKey = projectKey;
                if (_.isEmpty(configInfo)) {
                    this.formItems = _.clone(defConfig);

                } else {
                    this.formItems = configInfo;
                }
            }
        },
        components: {
            dictSelect
        }
    };
</script>