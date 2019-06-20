<template>
    <div>
        <Form ref="formItems" :model="formItems" :rules="formRules" :label-width="120" inline>

            <Row v-show="windowName != 'headless'">
                <Col span="24">
                    <Form-item label="代码输出方式" prop="outputType" style="width: 95%">
                        <dict-radio v-model="formItems.outputType" :disabled="disableInput"
                                    :kind="this.constants.dicts.dictKinds.OUTPUT_TYPE_DICT"></dict-radio>
                    </Form-item>
                </Col>

                <Col span="24" v-show="formItems.outputType ==='1'">
                    <Form-item label="代码输出根目录" prop="projectOutputDir" style="width: 95%">
                        <file-chooser v-model="formItems.projectOutputDir" :disabled="disableInput"
                                      dialogType="directory" changeCurPath="1"></file-chooser>
                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="模板类型" prop="templateType" style="width: 95%">
                        <dict-radio v-model="formItems.templateType" :disabled="disableInput"
                                    :kind="this.constants.dicts.dictKinds.TEMPLATE_TYPE_DICT"></dict-radio>
                    </Form-item>
                </Col>
            </Row>


            <div v-show="formItems.templateType=='git'" style="margin-bottom: 20px">
                <Form-item label="git url" prop="gitUrl" style="width: 95%">
                    <Input v-model="formItems.gitUrl" :maxlength="200" :disabled="disableInput">
                    <Button slot="append" icon="ios-search" @click="repoChoose">选择</Button>
                    </Input>

                    <git-repo-list @itemChoose="chooseGitRepo" repoName="templateRepo" ref="gitRepoList"
                                   :showButton="false"></git-repo-list>
                </Form-item>

                <Form-item label="branchName" prop="branchName" style="width: 95%">
                    <Input v-model="formItems.branchName" :maxlength="200" :disabled="disableInput"></Input>
                </Form-item>

                <Form-item label="模板相对路径" prop="templateRelativePath" style="width: 95%">
                    <Input v-model="formItems.templateRelativePath" :maxlength="200"
                           :disabled="disableInput"></Input>
                </Form-item>


                <Collapse simple>
                    <Panel name="1">
                        git鉴权&other
                        <p slot="content">


                            <Form-item label="用户名" prop="userName" style="width: 95%">
                                <Input v-model="formItems.userName" :maxlength="200" :disabled="disableInput"></Input>
                            </Form-item>

                            <Form-item label="密码" prop="password" style="width: 95%">
                                <Input type="password" v-model="formItems.password" :maxlength="200"
                                       :disabled="disableInput"></Input>
                            </Form-item>
                        </p>
                    </Panel>
                </Collapse>
            </div>

            <div v-show="formItems.templateType=='local' || formItems.templateType==null ">
                <Row>
                    <Col span="24">
                        <Form-item label="模板根目录" prop="templateBaseDir" style="width: 95%">
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

                <Button type="info" @click="fetchGitRepo" :loading="loading" v-show="formItems.templateType=='git'">
                    <Icon type="android-arrow-down"></Icon>
                    拉取git仓库
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
    import gitRepoList from '@/views/repos/git/list';

    var _ = require('underscore')

    var defConfig = {
        outputType: '1',
        templateBaseDir: '',
        projectOutputDir: '',
        templateType: 'local',
        'gitUrl': '',
        'branchName': 'master',
        'templateRelativePath': '',
        'userName': '',
        'password': ''
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
                type: Object
            }
        },
        data: function () {
            let formRules = {
                templateBaseDir: [{type: 'string', required: true, message: '模板根目录不能为空', trigger: 'blur'}],
                projectOutputDir: [{type: 'string', required: true, message: '代码输出目录不能为空', trigger: 'blur'}],
                gitUrl: [{type: 'string', required: false, message: 'gitUrl不能为空', trigger: 'blur'}],
                branchName: [{type: 'string', required: false, message: 'branchName不能为空', trigger: 'blur'}]
            };
            let formItems = this.configInfo;
            if (_.isEmpty(formItems)) {
                formItems = _.clone(defConfig)
            }
            return {
                projectKey: this.defaultProjectKey,
                formItems,
                formRules: formRules,
                constants,
                disableInput: false,
                loading: false
            };
        },
        watch: {
            'formItems.templateType': function (newVal) {
                this._templateTypeChange();
            },
            'formItems.outputType': function (newVal) {
                this._outputTypeChange();
            }
        },
        computed: {
            windowName: function () {
                let windowName = this.$store.state.autoCode.profile['browserWindowName'];
                if (windowName === 'headless') {
                    this.formRules.projectOutputDir[0].required = false;
                }
                return windowName;
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
                    if (!configInfo.templateType) {
                        configInfo.templateType = 'local';
                    }
                    if (!configInfo.outputType) {
                        configInfo.outputType = '1';
                    }
                    this.formItems = configInfo;
                }
                this._templateTypeChange(this.formItems.templateType === 'maven');
            },
            fetchGitRepo() {
                this.$refs['formItems'].validate((valid) => {
                    if (!valid) {
                        this.$Message.error({
                            content: '参数验证失败，请检验参数',
                            duration: 3
                        });
                        return false;
                    }
                    let gitRepoParam = {
                        'gitUrl': this.formItems.gitUrl,
                        'branchName': this.formItems.branchName,
                        'userName': this.formItems.userName,
                        'password': this.formItems.password
                    };
                    requestUtils.postSubmit(this, constants.urls.repo.git.fetchRepo, gitRepoParam, function (data) {
                        this.$Message.success({
                            content: '拉取仓库地址信息成功，耗时(s)：' + data['costTime'],
                            duration: 5
                        });
                    }, null, true);
                });
            },
            _templateTypeChange: function () {
                let isGit = this.formItems.templateType === 'git';
                this.formRules.gitUrl[0].required = isGit;
                this.formRules.branchName[0].required = isGit;
                this.formRules.templateBaseDir[0].required = !isGit;
            },
            _outputTypeChange: function () {
                this.formRules.projectOutputDir[0].required = (this.formItems.outputType === '1');
            },
            chooseGitRepo(gitRepo) {
                let _this = this;
                this.$nextTick(() => {
                    _this.formItems.gitUrl = gitRepo.gitUrl;
                    _this.formItems.branchName = gitRepo.branchName;
                    _this.formItems.templateRelativePath = gitRepo.templateRelativePath;
                });
            },
            repoChoose() {
                this.$refs.gitRepoList.open();
            }
        },
        components: {
            dictSelect,
            fileChooser,
            dictRadio,
            gitRepoList
        }
    };
</script>
