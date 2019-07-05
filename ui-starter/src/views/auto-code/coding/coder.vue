<template>
    <div>
        <Form ref="formItems" :model="formItems" :rules="formRules" :label-width="120" inline>

            <Row v-show="windowName!='headless'">
                <Col span="24">
                    <Form-item label="代码输出方式" prop="outputType" style="width: 95%">
                        <dict-radio v-model="formItems.outputType" :disabled="disableInput"
                                    :kind="this.constants.dicts.dictKinds.OUTPUT_TYPE_DICT"></dict-radio>
                    </Form-item>
                </Col>


                <Col span="24" v-show="formItems.outputType ==='1'">
                    <Form-item label="代码输出目录" prop="projectOutputDir" style="width: 96%">
                        <file-chooser v-model="formItems.projectOutputDir" :disabled="true"
                                      dialogType="directory"></file-chooser>
                    </Form-item>
                </Col>
            </Row>

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
                        <file-chooser v-model="formItems.templateBaseDir" :disabled="true" changeCurPath="1"
                                      dialogType="directory"></file-chooser>
                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="模板列表" style="width: 100%">
                        <template-list ref="templateList" :sid="formItems.sid"
                                       v-on:fetchTemplateList="connectLogServer"></template-list>
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


            <Row v-show="windowName != 'headless' && formItems.outputType ==='1'">
                <Col span="24">
                    <Form-item label="是否覆盖文件" prop="overrideFlag" style="width: 96%">
                        <dict-radio v-model="formItems.overrideFlag" :disabled="disableInput"
                                    :kind="this.constants.dicts.dictKinds.STD_BOOLEAN_DICT"></dict-radio>
                    </Form-item>
                </Col>
            </Row>


            <FormItem v-show="resourceType !== 'database'">
                <Button type="primary" @click="execute" :loading="loading">
                    <Icon type="ios-play"></Icon>
                    生成代码
                </Button>
            </FormItem>


            <Row v-show="resourceType == 'database'">
                <Col span="24">
                    <tableModelJson v-on:genCode="genCode"></tableModelJson>
                </Col>
            </Row>


            <Row v-show="windowName == 'headless' || formItems.outputType ==='2'">
                <Col span="24">
                    <Form-item label="执行结果文件" style="width: 100%">
                        <result-info ref="resultInfo"></result-info>
                    </Form-item>
                </Col>
            </Row>


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
    import tableModelJson from '@/views/auto-code/coding/tableModelJson';
    import resultInfo from '@/views/common/log/resultInfo';

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
                    outputType: '1',
                    templateBaseDir: '',
                    tableModelPath: '',
                    projectOutputDir: '',
                    overrideFlag: 'false',
                    tableModelDir: '',
                    templateIds: '',
                    dataModelType: 'tableModel',
                    resourceType: 'database',
                    frontContent: '',
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
            if (myData.windowName === 'headless') {
                validateSet.projectOutputDir[0].required = false;
                myData.formItem.outputType = '2';
            }
            return myData;
        },
        components: {
            fileChooser,
            dictRadio,
            templateList,
            logConsole,
            autoMakeInfo,
            tableModelJson,
            resultInfo
        },
        mounted: function () {
            var configList = this.$store.state.autoCode.defaultProjectConfig;
            var configObject = configUtil.getConfigObject(configList, constants.bizKeys.configs.pathConfig.configKey);
            this.formItems.templateBaseDir = configObject[constants.bizKeys.configs.pathConfig.templateBaseDir];
            this.formItems.projectOutputDir = configObject[constants.bizKeys.configs.pathConfig.projectOutputDir];
            this.formItems.tableModelDir = configObject[constants.bizKeys.configs.pathConfig.tableModelDir];
            this.formItems.outputType = configObject[constants.bizKeys.configs.pathConfig.outputType];
            if (!this.formItems.outputType) {
                this.formItems.outputType = '1';
            }
            this.formValidateChange();
            this.loadBrowserPassContent();
            let promise = this.$store.dispatch(constants.types.LOAD_SYSTEM_PROFILE, {_vue: this});
            promise.then(function (profile) {
            });
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
            storeOutputType: function () {
                return this.$store.getters.getConfigObjectVal(constants.bizKeys.configs.pathConfig.configKey,
                    constants.bizKeys.configs.pathConfig.outputType);
            },
            pathConfig: function () {
                return this.$store.getters.getConfigObject(constants.bizKeys.configs.pathConfig.configKey);
            },
            resourceType: function () {
                return this.formItems.resourceType;
            },
            windowName: function () {
                return this.$store.state.autoCode.profile['browserWindowName'];
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
            storeOutputType: function (newVal) {
                if (!newVal) {
                    newVal = '1';
                }
                this.formItems.outputType = newVal;
            },
            resourceType: function (newVal) {
                if (newVal === 'file') {
                    this.formRules.tableModelPath[0].required = true;
                    this.formRules.frontContent[0].required = false;
                } else if (newVal === 'front') {
                    this.formRules.tableModelPath[0].required = false;
                    this.formRules.frontContent[0].required = true;
                }
            },
            'formItems.outputType': function (newVal) {
                this.formRules.projectOutputDir[0].required = newVal === '1';
            }
        },
        methods: {
            execute(callback) {
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
                            content: '生成代码成功，请查看相关目录查看',
                            duration: 3
                        });
                        this.appendResultInfo(data);
                        if (callback) {
                            callback.call(this, true);
                        }
                    }, function (data) {
                        var message = data['message'];
                        if (message === null || message === '') {
                            message = '执行失败，原因未知，请查看运行日志';
                        }
                        _this.$refs.logConsole.appendMessage(message);
                        if (callback) {
                            callback.call(this, false);
                        }
                    }, true);
                });
            },
            appendResultInfo(data) {
                let zipFileName = data['zipFileName'];
                if (zipFileName === null || zipFileName === '') {
                    return;
                }
                let projectKey = this.$store.state.autoCode.defaultProjectKey;
                var host = window.location.host;
                let zipUrl = window.location.protocol + '//' + host + '/zip/' + projectKey + '/' + zipFileName;
                let resultInfo = {
                    zipUrl,
                    'startTime': data['startTime'],
                    'costTime': data['costTime'],
                    'outputDir': data['outputDir'],
                    'templateResultList': data['templateResultList'],
                    projectKey
                }
                this.$refs.resultInfo.appendResultInfo(resultInfo);
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
            connectLogServer() {
                this.$refs.logConsole.connectServer();
            },
            genCode(value) {
                if (value === null || value === '') {
                    this.$Message.error({
                        content: '请先选中表模型进行生成！',
                        duration: 5
                    })
                    return;
                }
                this.formItems.tableModelPath = value;
                this.execute();
            }
        }
    };
</script>

<style scoped>

</style>
