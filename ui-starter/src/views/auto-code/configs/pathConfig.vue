<template>
    <div>
        <Form ref="formItems" :model="formItems" :rules="formRules" :label-width="120" inline>

            <Row>
                <Col span="24">
                    <Form-item label="tableModel存放目录" prop="tableModelDir" style="width: 80%">
                        <file-chooser v-model="formItems.tableModelDir" :disabled="disableInput" dialogType="directory"
                                      changeCurPath="1"></file-chooser>
                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="代码输出根目录" prop="projectOutputDir" style="width: 80%">
                        <file-chooser v-model="formItems.projectOutputDir" :disabled="disableInput"
                                      dialogType="directory" changeCurPath="1"></file-chooser>
                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="模板类型" prop="templateType" style="width: 80%">
                        <dict-radio v-model="formItems.templateType" :disabled="disableInput"
                                    :kind="this.constants.dicts.dictKinds.TEMPLATE_TYPE_DICT"></dict-radio>
                    </Form-item>
                </Col>
            </Row>

            <div v-show="formItems.templateType=='maven'">
                <Form-item label="groupId (maven)" prop="groupId" style="width: 80%">
                    <Input v-model="formItems.groupId" :maxlength="200" :disabled="disableInput"></Input>
                </Form-item>

                <Form-item label="artifactId (maven)" prop="artifactId" style="width: 80%">
                    <Input v-model="formItems.artifactId" :maxlength="200" :disabled="disableInput"></Input>
                </Form-item>

                <Form-item label="version (maven)" prop="version" style="width: 80%">
                    <Input v-model="formItems.version" :maxlength="200" :disabled="disableInput"></Input>
                </Form-item>
            </div>

            <div v-show="formItems.templateType=='local' || formItems.templateType==null ">
                <Row>
                    <Col span="24">
                        <Form-item label="模板根目录" prop="templateBaseDir" style="width: 80%">
                            <file-chooser v-model="formItems.templateBaseDir" :disabled="disableInput" changeCurPath="1"
                                          dialogType="directory"></file-chooser>
                        </Form-item>
                    </Col>
                </Row>
            </div>

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
        templateBaseDir: '',
        tableModelDir: '',
        projectOutputDir: '',
        templateType: 'local'
    };

    export default {
        props: {
            defaultProjectKey: {
                type: String
            },
            configKey: {
                type: String,
                default: 'pathConfig'
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
                    templateBaseDir: [{type: 'string', required: true, message: '模板根目录不能为空', trigger: 'blur'}],
                    groupId: [{type: 'string', required: false, message: 'groupId不能为空', trigger: 'blur'}],
                    artifactId: [{type: 'string', required: false, message: 'artifactId不能为空', trigger: 'blur'}],
                    version: [{type: 'string', required: false, message: 'version不能为空', trigger: 'blur'}],
                    tableModelDir: [{type: 'string', required: true, message: '表模型输出目录不能为空', trigger: 'blur'}],
                    projectOutputDir: [{type: 'string', required: true, message: '代码输出目录不能为空', trigger: 'blur'}]
                },
                constants,
                disableInput: false,
                loading: false
            };
        },
        watch: {
            'formItems.templateType': function (newVal) {
                var isMaven = (newVal === 'maven');
                var isLocal = (newVal === 'local' || newVal === null);
                this._templateTypeChange(isMaven);
            }
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
                            content: '保存数据库配置成功',
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
                    if (!configInfo.templateType) {
                        configInfo.templateType = 'local';
                    }
                    this.formItems = configInfo;
                }
                this._templateTypeChange(this.formItems.templateType === 'maven');
            },
            _templateTypeChange: function (isMaven) {
                this.formRules.templateBaseDir[0].required = !isMaven;
                this.formRules.groupId[0].required = isMaven;
                this.formRules.artifactId[0].required = isMaven;
                this.formRules.version[0].required = isMaven;
            }
        },
        components: {
            dictSelect,
            fileChooser,
            dictRadio
        }
    };
</script>
