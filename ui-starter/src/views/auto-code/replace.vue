<template>
    <div>
        <Form ref="formItems" :model="formItems" :rules="formRules" :label-width="120" inline>

            <Card>
                <p slot="title">
                    <Icon type="folder"></Icon>
                    目录配置
                </p>

                <Row>
                    <Col span="24">
                        <Form-item label="说明" style="width: 95%">
                            用于进行基础工程全路径替换，可同时替换内容和文件目录。
                            可以称为盗版神器，如将 apache官方工具包转换为自己公司工程：
                            <br/>
                            内容表达式：com.apache.hello=com.mycompany.myproject;
                            目录表达式：com/apache/hello=com/mycompany/myproject
                            <br/>
                            主要用于将dummy（呆子）工程的一键转换为新开发工程；

                            <br/>
                            <b>请勿将源目录和输出目录设置为有任何包含关系，否则容易出现递归和文件混乱。</b>
                            <br/>
                            分为内容表达式 & 目录转换表达式, 请使用英文逗号区分多个表达式；
                            如：<br/>
                            忽略表达式：只复制，不替换，但是只针对当前文件名和内容，不针对子文件。
                            <br/>
                            丢弃表达式： target, *.iml, .svn, node_modules, .git 建议使用以上值，将工作目录内容丢弃；
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="工程类型" prop="templateType" style="width: 95%">
                            <dict-radio v-model="formItems.templateType" :disabled="disableInput"
                                        :kind="this.constants.dicts.dictKinds.TEMPLATE_TYPE_DICT"></dict-radio>
                        </Form-item>
                    </Col>
                </Row>


                <Row v-show="formItems.templateType=='local'">
                    <Col span="24">
                        <Form-item label="源文件路径" prop="srcPath" style="width: 95%">
                            <file-chooser v-model="formItems.srcPath" :disabled="disableInput"
                                          dialogType="directory"></file-chooser>
                        </Form-item>
                    </Col>
                </Row>

                <div v-show="formItems.templateType=='git'" STYLE="margin-bottom: 20px">
                    <Form-item label="git url" prop="gitUrl" style="width: 95%">
                        <Input v-model="formItems.gitUrl" :maxlength="200" :disabled="disableInput">
                        <Button slot="append" icon="ios-search" @click="repoChoose">选择</Button>
                        </Input>
                        <git-repo-list @itemChoose="chooseGitRepo" repoName="baseProjectRepo" ref="gitRepoList"
                                       :showButton="false"></git-repo-list>
                    </Form-item>

                    <Form-item label="branchName" prop="branchName" style="width: 50%">
                        <Input v-model="formItems.branchName" :maxlength="200" :disabled="disableInput"></Input>

                        <Button type="info" @click="fetchGitRepo" :loading="loading">
                            <Icon type="android-arrow-down"></Icon>
                            拉取git仓库
                        </Button>
                    </Form-item>

                    <Collapse simple>
                        <Panel name="1">
                            git鉴权&other
                            <p slot="content">
                                <Form-item label="用户名" prop="userName" style="width: 50%">
                                    <Input v-model="formItems.userName" :maxlength="200"
                                           :disabled="disableInput"></Input>
                                </Form-item>

                                <Form-item label="密码" prop="password" style="width: 50%">
                                    <Input v-model="formItems.password" :maxlength="200" type="password"
                                           :disabled="disableInput"></Input>
                                </Form-item>
                            </p>
                        </Panel>
                    </Collapse>
                </div>


                <Row>
                    <Col span="24">
                        <Form-item label="忽略处理表达式，只复制不替换" prop="ignoreExp" style="width: 95%">
                            <Input v-model="formItems.ignoreExp" type="textarea" :autosize="{minRows: 2,maxRows: 5}"
                                   :disabled="disableInput"></Input>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="丢弃表达式，不复制丢弃" prop="throwExp" style="width: 95%">
                            <Input v-model="formItems.throwExp" type="textarea" :autosize="{minRows: 2,maxRows: 5}"
                                   :disabled="disableInput"></Input>
                        </Form-item>
                    </Col>
                </Row>


            </Card>


            <Card>
                <p slot="title">
                    <Icon type="android-car"></Icon>
                    表达式 & 执行
                </p>


                <Row>
                    <Col span="24">
                        <Form-item label="说明" style="width: 95%">
                            功能说明：因为目前文件统一使用UTF-8格式，如果非UTF-8文件可能出现乱码，请注意！<br/>
                            替换表达式: 可以对目录名称和文本内容进行替换，如 oldName=newName 将会将所有包含oldName字符串的文件夹名称和文本内容全部替换为newName; <br/>
                            目录转换表达式：
                            com/apache/hello=com/mycompany/group/myprj 延长目录；<br/>
                            com/apache/hello=com/myprj 缩短目录；<br/>
                            com/apache/hello=com/mycompany/myprj 等长更名等；
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="替换表达式" prop="replaceExp" style="width: 95%">
                            <Input v-model="formItems.replaceExp" type="textarea" :autosize="{minRows: 2,maxRows: 5}"
                                   :disabled="disableInput"></Input>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="目录转换表达式" prop="dirReplaceExp" style="width: 95%">
                            <Input v-model="formItems.dirReplaceExp" type="textarea" :autosize="{minRows: 2,maxRows: 5}"
                                   :disabled="disableInput"></Input>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="替换类型" prop="replaceType" style="width: 95%">
                            <dict-radio v-model="formItems.replaceType" :disabled="disableInput"
                                        :kind="this.constants.dicts.dictKinds.REPLACE_TYPE_DICT"></dict-radio>
                        </Form-item>
                    </Col>
                </Row>


                <FormItem>
                    <Button type="info" @click="save" :loading="loading">
                        <Icon type="document"></Icon>
                        保存配置信息
                    </Button>

                    <Button type="primary" @click="execute" :loading="loading">
                        <Icon type="android-done"></Icon>
                        执行替换
                    </Button>
                </FormItem>


                <Row>
                    <Col span="24">
                        <Form-item label="执行结果文件" style="width: 100%">
                            <result-info ref="resultInfo"></result-info>
                        </Form-item>
                    </Col>
                </Row>


                <Row>
                    <Col span="22">
                        <log-console :sid="sid" ref="logConsole"></log-console>
                    </Col>
                </Row>

            </Card>
        </Form>

    </div>
</template>

<script>
    import constants from '@/constants/constants';
    import dictSelect from '@/views/common/dict/DictSelect';
    import fileChooser from '@/views/common/file/fileChooser';
    import dictRadio from '@/views/common/dict/DictRadio';
    import requestUtils from '@/request/requestUtils.js';
    import resultInfo from '@/views/common/log/resultInfo';
    import logConsole from '@/views/common/log/log-console';
    import gitRepoList from '@/views/repos/git/list';

    var _ = require('underscore')

    var validateSet = {
        srcPath: [{type: 'string', required: true, message: '原路径不能为空', trigger: 'blur'}],
        gitUrl: [{type: 'string', required: true, message: 'gitUrl不能为空', trigger: 'blur'}],
        branchName: [{type: 'string', required: true, message: 'branchName不能为空', trigger: 'blur'}],
        ignoreExp: [{type: 'string', required: false, message: '原路径不能为空', trigger: 'blur'}],
        throwExp: [{type: 'string', required: false, message: '原路径不能为空', trigger: 'blur'}],
        replaceType: [{type: 'string', required: true, message: '替换类型不能为空', trigger: 'blur'}]
    };

    let defConfig = {
        srcPath: '',
        templateType: 'git',
        gitUrl: '',
        branchName: 'master',
        userName: '',
        password: '',
        ignoreExp: '',
        throwExp: '',
        replaceExp: '',
        dirReplaceExp: '',
        replaceType: ''
    };

    export default {
        name: 'replace',
        props: {
            bindId: {
                type: String,
                default: 'autoCodeReplaceUtil'
            }
        },
        data: function () {
            return {
                formItems: _.clone(defConfig),
                formRules: validateSet,
                constants,
                disableInput: false,
                loading: false,
                message: '',
                sid: 'sid_' + new Date().getTime()
            };
        },
        watch: {
            'formItems.templateType': function (newVal) {
                this._templateTypeChange(newVal);
            }
        },
        mounted() {
            var _this = this;
            let promise = this.$store.dispatch(constants.types.GET_BIND_RESOURCE, {bindId: this.bindId});
            promise.then((data) => {
                if (_.isEmpty(data)) {
                    this._templateTypeChange();
                    return;
                }
                if (!data['branchName']) {
                    data['branchName'] = 'master';
                }
                if (!data['templateType']) {
                    data['templateType'] = 'git';
                }
                _this.formItems = data;
                this._templateTypeChange();
            });
        },
        methods: {
            save: function () {
                this.$refs['formItems'].validate((valid) => {
                    if (!valid) {
                        return false;
                    }
                    let params = {
                        bindId: this.bindId,
                        configMap: this.formItems,
                        '_vue': this
                    };
                    let promise = this.$store.dispatch(constants.types.SAVE_BIND_RESOURCE, params);
                    promise.then((data) => {
                        this.$Message.success({
                            content: '保存配置信息成功',
                            duration: 5
                        });
                    });
                });
            },
            execute() {
                this.$refs['formItems'].validate((valid) => {
                    if (!valid) {
                        this.$Message.error({
                            content: '参数有误，请检查...',
                            duration: 5
                        })
                        return false;
                    }
                    let params = _.clone(this.formItems);
                    params['sid'] = this.sid;
                    var datas = requestUtils.serializeObject(params, true, true);
                    this.connectLogServer();
                    requestUtils.postSubmit(this, constants.urls.autoCode.replace.execute, datas, function (data) {
                        this.$Message.success({
                            content: '执行成功',
                            duration: 5
                        });
                        this.appendResultInfo(data);
                    }, null, true);
                });
            },
            fetchGitRepo() {
                this.$refs['formItems'].validate((valid) => {
                    if (!valid) {
                        this.$Message.error({
                            content: '参数验证失败，请检验参数',
                            duration: 5
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
            _templateTypeChange: function (newVal) {
                if (!newVal) {
                    newVal = this.formItems.templateType;
                }
                let isGit = newVal === 'git';
                this.formRules.gitUrl[0].required = isGit;
                this.formRules.branchName[0].required = isGit;
                this.formRules.srcPath[0].required = !isGit;
            },
            appendResultInfo(data) {
                let zipFileName = data['zipFileName'];
                if (zipFileName === null || zipFileName === '') {
                    return;
                }
                var host = window.location.host;
                let zipUrl = window.location.protocol + '//' + host + '/zip/sampleProject/' + zipFileName;
                let resultInfo = {
                    zipUrl,
                    'startTime': data['startTime'],
                    'costTime': data['costTime']
                }
                this.$refs.resultInfo.appendResultInfo(resultInfo);
            },
            chooseGitRepo(gitRepo) {
                this.formItems.gitUrl = gitRepo.gitUrl;
                this.formItems.branchName = gitRepo.branchName;
                if (gitRepo['extConfig']) {
                    let extConfig = gitRepo['extConfig'];
                    if (extConfig['ignoreExp']) {
                        this.formItems.ignoreExp = extConfig['ignoreExp'];
                    }
                    if (extConfig['throwExp']) {
                        this.formItems.throwExp = extConfig['throwExp'];
                    }
                    if (extConfig['replaceExp']) {
                        this.formItems.replaceExp = extConfig['replaceExp'];
                    }
                    if (extConfig['dirReplaceExp']) {
                        this.formItems.dirReplaceExp = extConfig['dirReplaceExp'];
                    }
                }
            },
            repoChoose() {
                this.$refs.gitRepoList.open();
            },
            connectLogServer() {
                this.$refs.logConsole.connectServer();
            }
        },
        components: {
            dictSelect,
            fileChooser,
            dictRadio,
            resultInfo,
            logConsole,
            gitRepoList
        }
    };
</script>
