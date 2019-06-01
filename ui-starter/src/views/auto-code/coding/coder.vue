<template>
    <div>
        <Form ref="formItems" :model="formItems" :rules="formRules" :label-width="120" inline>


            <Row>
                <Col span="24">
                    <Form-item label="模板类型" style="width: 96%">
                        <dict-radio v-model="pathConfig.templateType" :disabled="true"
                                    :kind="this.constants.dicts.dictKinds.TEMPLATE_TYPE_DICT"></dict-radio>
                        <auto-make-info v-show="pathConfig.templateType == 'maven'"></auto-make-info>
                    </Form-item>
                </Col>
            </Row>

            <Row v-show="pathConfig.templateType == 'local'">
                <Col span="24">
                    <Form-item label="模板根目录" prop="templateBaseDir" style="width: 96%">
                        <file-chooser v-model="formItems.templateBaseDir" :disabled="true" changeCurPath="1" dialogType="directory"></file-chooser>
                    </Form-item>
                </Col>
            </Row>


            <Row v-show="pathConfig.templateType == 'maven'">
                <Col span="24">
                    <Form-item label="强制刷新maven资源" prop="flushMaven" style="width: 96%">
                        <dict-radio v-model="formItems.flushMaven" :disabled="disableInput"
                                    :kind="this.constants.dicts.dictKinds.STD_YESNO_DICT"></dict-radio>
                        请配合maven setting.xml 里面的 &lt;updatePolicy&gt;always&lt;/updatePolicy&gt;使用。
                    </Form-item>
                </Col>
            </Row>


            <Row>
                <Col span="24">
                    <Form-item label="数据模型类型" style="width: 96%">
                        <dict-radio v-model="formItems.dataModelType" :disabled="false"
                                    :kind="this.constants.dicts.dictKinds.DATA_MODEL_TYPE_DICT"></dict-radio>
                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="输入源类型" style="width: 96%">
                        <dict-radio v-model="formItems.resourceType" :disabled="false"
                                    :kind="this.constants.dicts.dictKinds.RESOURCE_TYPE_DICT"></dict-radio>
                    </Form-item>
                </Col>
            </Row>


            <Row v-show="resourceType == 'file'">
                <Col span="24">
                    <Form-item label="数据源文件路径" prop="tableModelPath" style="width: 96%">
                        <file-chooser v-model="formItems.tableModelPath" :disabled="disableInput"
                                      dialogType="open" fileExt="*.xml"
                                      :parentPath="formItems.tableModelDir"></file-chooser>
                    </Form-item>
                </Col>
            </Row>

            <Row v-show="resourceType == 'front'">
                <Col span="24">
                    <Form-item label="前台源内容（用于数据处理）" prop="frontContent" style="width: 96%">
                        <Input v-model="formItems.frontContent" type="textarea"
                               :autosize="{minRows:10,maxRows: 10}"></Input>
                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="代码输出目录" prop="projectOutputDir" style="width: 96%">
                        <file-chooser v-model="formItems.projectOutputDir" :disabled="true"
                                      dialogType="directory"></file-chooser>
                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="是否覆盖文件" prop="overrideFlag" style="width: 96%">
                        <dict-radio v-model="formItems.overrideFlag" :disabled="disableInput"
                                    :kind="this.constants.dicts.dictKinds.STD_BOOLEAN_DICT"></dict-radio>
                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="是否直接输出到前台" style="width: 96%">
                        <dict-radio v-model="formItems.outputFrontType" :disabled="disableInput"
                                    :kind="this.constants.dicts.dictKinds.STD_YESNO_DICT"></dict-radio>
                    </Form-item>
                </Col>
            </Row>
            <Row>
                <Col span="24">
                    <Form-item label="模板列表" style="width: 100%">
                        <template-list ref="templateList" :flush-maven="formItems.flushMaven" :sid="formItems.sid"
                                       v-on:fetchTemplateList="connectLogServer"></template-list>
                    </Form-item>
                </Col>
            </Row>


            <FormItem>
                <Button type="primary" @click="execute" :loading="loading">
                    <Icon type="ios-play"></Icon>
                    生成代码
                </Button>
            </FormItem>

            <Row>
                <Col span="22">
                    <log-console :sid="formItems.sid" ref="logConsole"></log-console>
                </Col>
            </Row>
        </Form>

    </div>
</template>

<script>
    import constants from '@/constants/constants';
    import dictRadio from '@/views/common/dict/DictRadio';
    import templateList from './templateList';
    import requestUtils from '@/request/requestUtils.js';
    import * as configUtil from '@/libs/configUtil.js';
    import fileChooser from '@/views/common/file/fileChooser';
    import logConsole from '@/views/common/log/log-console';
    import autoMakeInfo from './autoMakeInfo';

    var _ = require('underscore')

    var validateSet = {
        templateBaseDir: [{type: 'string', required: true, message: 'autoXml路径不能为空', trigger: 'blur'}],
        tableModelPath: [{type: 'string', required: true, message: '输入源文件存储路径不能为空', trigger: 'blur'}],
        projectOutputDir: [{type: 'string', required: true, message: '代码输出目录不能为空', trigger: 'blur'}],
        frontContent: [{type: 'string', required: false, message: '内容不能为空', trigger: 'blur'}]
    };

    export default {
        name: 'coder',
        data() {
            var configType = this.$route.query['configType'];
            var myData = {
                formItems: {
                    templateBaseDir: '',
                    tableModelPath: '',
                    projectOutputDir: '',
                    overrideFlag: 'false',
                    tableModelDir: '',
                    templateIds: '',
                    flushMaven: '2',
                    dataModelType: 'tableModel',
                    resourceType: 'file',
                    frontContent: '',
                    outputFrontType: '2',
                    sid: 'sid_' + new Date().getTime()
                },
                formRules: validateSet,
                disableInput: false,
                loading: false,
                constants
            };
            if (configType === 'ump') {
                myData.formItems.dataModelType = 'json';
                myData.formItems.resourceType = 'front';
            }
            return myData;
        },
        components: {
            fileChooser,
            dictRadio,
            templateList,
            logConsole,
            autoMakeInfo
        },
        mounted: function () {
            var configList = this.$store.state.autoCode.defaultProjectConfig;
            var configObject = configUtil.getConfigObject(configList, constants.bizKeys.configs.pathConfig.configKey);
            this.formItems.templateBaseDir = configObject[constants.bizKeys.configs.pathConfig.templateBaseDir];
            this.formItems.projectOutputDir = configObject[constants.bizKeys.configs.pathConfig.projectOutputDir];
            this.formItems.tableModelDir = configObject[constants.bizKeys.configs.pathConfig.tableModelDir];
            this.formValidateChange();
            this.loadBrowserPassContent();
        },
        computed: {
            storeTemplateBaseDir: function () {
                // getter 没有名称， action 和 mutation也是
                return this.$store.getters.getConfigObjectVal(constants.bizKeys.configs.pathConfig.configKey,
                    constants.bizKeys.configs.pathConfig.templateBaseDir);
            },
            storeProjectOutputDir: function () {
                return this.$store.getters.getConfigObjectVal(constants.bizKeys.configs.pathConfig.configKey,
                    constants.bizKeys.configs.pathConfig.projectOutputDir);
            },
            storeTableModelDir: function () {
                return this.$store.getters.getConfigObjectVal(constants.bizKeys.configs.pathConfig.configKey,
                    constants.bizKeys.configs.pathConfig.tableModelDir);
            },
            pathConfig: function () {
                return this.$store.getters.getConfigObject(constants.bizKeys.configs.pathConfig.configKey);
            },
            resourceType: function () {
                return this.formItems.resourceType;
            }
        },
        watch: {
            storeTemplateBaseDir: function (newVal) {
                this.formItems.templateBaseDir = newVal;
            },
            storeProjectOutputDir: function (newVal) {
                this.formItems.projectOutputDir = newVal;
            },
            storeTableModelDir: function (newVal) {
                this.formItems.tableModelDir = newVal;
            },
            resourceType: function (newVal) {
                if (newVal === 'file') {
                    this.formRules.tableModelPath[0].required = true;
                    this.formRules.frontContent[0].required = false;
                } else if (newVal === 'front') {
                    this.formRules.tableModelPath[0].required = false;
                    this.formRules.frontContent[0].required = true;
                }
            }
        },
        methods: {
            execute() {
                var _this = this;
                if (!this.$store.state.autoCode.defaultProjectKey) {
                    this.$Message.error({
                        content: '还未设置默认项目，请先进行配置...',
                        duration: 5
                    })
                    return;
                }
                var isLocal = this.pathConfig.templateType === 'local';
                validateSet.templateBaseDir[0].required = isLocal;

                this.$refs['formItems'].validate((valid) => {
                    if (!valid) {
                        this.$Message.error({
                            content: '参数有误，请检查...',
                            duration: 5
                        })
                        return false;
                    }
                    let choosedTemplateIds = this.$refs['templateList'].getChoosedTemplateIds();
                    if (_.isEmpty(choosedTemplateIds)) {
                        this.$Message.error({
                            content: '请先拉取模板列表，并选择要生成的代码模块',
                            duration: 5
                        })
                        return false;
                    }
                    this.formItems.templateIds = choosedTemplateIds.join(',');
                    var datas = requestUtils.serializeObject(_.clone(this.formItems), true, true);
                    datas.projectKey = this.$store.state.autoCode.defaultProjectKey;
                    _this.$refs.logConsole.connectServer();
                    requestUtils.postSubmit(this, constants.urls.autoCode.coder.execute, datas, function (data) {
                        this.$Message.success({
                            content: '生成tableModel成功',
                            duration: 3
                        });
                    }, function (data) {
                        var message = data['message'];
                        if (message === null || message === '') {
                            message = '执行失败，原因未知，请查看运行日志';
                        }
                        _this.$refs.logConsole.appendMessage(message);
                    }, true);
                });
            },
            loadBrowserPassContent() {
                requestUtils.postSubmit(this, constants.urls.sys.system.getProfileCache, {cacheKey: 'browserPassKey'}, function (data) {
                    this.formItems.frontContent = data['value'];
                }, function (data) {
                }, false);
            },
            clearMessage() {
                this.message = '';
            },
            formValidateChange: function () {
                if (this.formItems.resourceType === 'file') {
                    this.formRules.tableModelPath[0].required = true;
                    this.formRules.frontContent[0].required = false;
                } else if (this.formItems.resourceType === 'front') {
                    this.formRules.tableModelPath[0].required = false;
                    this.formRules.frontContent[0].required = true;
                }
            },
            connectLogServer () {
                this.$refs.logConsole.connectServer();
            },
            aboutTemplateGroup(){
                alert('haha');
            }
        }
    };
</script>

<style scoped>

</style>
