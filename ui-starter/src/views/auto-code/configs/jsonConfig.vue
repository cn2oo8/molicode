<template>
    <div>
        <Form ref="formItems" :model="formItems" :rules="formRules" :label-width="120" inline>

            <Row>
                <Col span="24">
                    <Form-item label="json扩展配置" prop="jsonConfig" style="width: 95%">
                        <Input v-model="formItems.jsonConfig" type="textarea"
                               :autosize="{minRows:10,maxRows: 10}"></Input>
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
    import dictSelect from '@/views/common/dict/DictSelect';
    import fileChooser from '@/views/common/file/fileChooser';
    import requestUtils from '@/request/requestUtils.js';
    import dictRadio from '@/views/common/dict/DictRadio';

    var _ = require('underscore')

    var defConfig = {
        jsonConfig: ''
    };

    export default {
        props: {
            defaultProjectKey: {
                type: String
            },
            configKey: {
                type: String,
                default: 'extConfig'
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
                formRules: {
                    jsonConfig: [{type: 'string', required: true, message: 'json配置信息，请输入json格式字符串', trigger: 'blur'}]
                },
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
                        this.$Message.error({
                            content: '参数验证失败，请检验参数',
                            duration: 3
                        });
                        return false;
                    }
                    requestUtils.postSubmit(this, constants.urls.conf.acConfig.save, this.getConfigData(), function (data) {
                        this.$Message.success({
                            content: '保存配置成功',
                            duration: 3
                        });
                    }, null, true);
                });
            },
            getConfigData() {
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
            dictSelect,
            fileChooser,
            dictRadio
        }
    };
</script>
