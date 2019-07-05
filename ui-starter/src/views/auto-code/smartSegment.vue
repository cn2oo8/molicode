<template>
    <div>
        <Form ref="formItems" :model="formItems" :rules="formRules" :label-width="120" inline>

            <Card>
                <p slot="title">
                    <Icon type="folder"></Icon>
                    smartSegment工程批量处理工具
                </p>

                <Row>
                    <Col span="24">
                        <Form-item label="说明" style="width: 95%">
                            用于进行智能化工程内文件处理；
                        </Form-item>
                    </Col>
                </Row>


                <Row>
                    <Col span="24">
                        <Form-item label="源文件路径" prop="srcPath" style="width: 95%">
                            <file-chooser v-model="formItems.srcPath" :disabled="disableInput"
                                          dialogType="directory"></file-chooser>
                        </Form-item>
                    </Col>
                </Row>


                <Row>
                    <Col span="24">
                        <Form-item label="groovy接口脚本" prop="segmentScriptPath" style="width: 95%">
                            <file-chooser v-model="formItems.segmentScriptPath" :disabled="disableInput"
                                          dialogType="open" fileExt="*.gsp"></file-chooser>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="处理白名单" prop="whiteListExp" style="width: 95%">
                            <Input v-model="formItems.whiteListExp" type="textarea" :autosize="{minRows: 2,maxRows: 5}"
                                   :disabled="disableInput"></Input>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="忽略处理表达式（不处理）" prop="ignoreExp" style="width: 95%">
                            <Input v-model="formItems.ignoreExp" type="textarea" :autosize="{minRows: 2,maxRows: 5}"
                                   :disabled="disableInput"></Input>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="json扩展配置" prop="jsonConfig" style="width: 95%">
                            <Input v-model="formItems.jsonConfig" type="textarea"
                                   :autosize="{minRows:3,maxRows: 5}"></Input>
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
                        执行处理
                    </Button>
                </FormItem>


                <Row>
                    <Col span="24">
                        <Form-item label="执行结果" style="width: 95%">
                            <log-console :sid="sid" ref="logConsole"></log-console>
                        </Form-item>
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
    import logConsole from '@/views/common/log/log-console';

    var _ = require('underscore')

    var validateSet = {
        srcPath: [{type: 'string', required: true, message: '原路径不能为空', trigger: 'blur'}],
        whiteListExp: [{type: 'string', required: true, message: '白名单', trigger: 'blur'}],
        ignoreExp: [{type: 'string', required: false, message: '忽略表达式', trigger: 'blur'}],
        throwExp: [{type: 'string', required: false, message: '丢弃表达式', trigger: 'blur'}],
        jsonConfig: [{type: 'string', required: false, message: 'json配置信息', trigger: 'blur'}],
        segmentScriptPath: [{type: 'string', required: true, message: '脚本路径不能为空', trigger: 'blur'}]
    };

    var defConfig = {
        srcPath: '',
        whiteListExp: '',
        ignoreExp: 'target, .iml, .svn, node_modules, .git',
        throwExp: '',
        jsonConfig: '',
        segmentScriptPath: ''
    };

    export default {
        name: 'smartSegment',
        props: {
            bindId: {
                type: String,
                default: 'smartSegmentConfig'
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
        mounted() {
            var _this = this;
            let promise = this.$store.dispatch(constants.types.GET_BIND_RESOURCE, {bindId: this.bindId});
            promise.then((data) => {
                if (_.isEmpty(data)) {
                    return;
                }
                _this.formItems = data;
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
                var _this = this;
                _this.$refs.logConsole.connectServer();
                this.$refs['formItems'].validate((valid) => {
                    if (!valid) {
                        this.$Message.error({
                            content: '参数有误，请检查...',
                            duration: 5
                        })
                        return false;
                    }
                    var datas = requestUtils.serializeObject(_.clone(_this.formItems), true, true);
                    datas['sid'] = _this.sid;
                    this.connectLogServer();
                    requestUtils.postSubmit(this, constants.urls.autoCode.smartSegment.execute, datas, function (data) {
                        this.$Message.success({
                            content: '执行智能片段处理成功',
                            duration: 5
                        });
                        this.message = data['message']
                    }, function (data) {
                        this.message = data['message']
                    }, true);
                });
            },
            connectLogServer() {
                this.$refs.logConsole.connectServer();
            }
        },
        components: {
            dictSelect,
            fileChooser,
            logConsole,
            dictRadio
        }
    };
</script>
