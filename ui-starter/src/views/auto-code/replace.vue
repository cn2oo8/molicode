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
                        <Form-item label="源文件路径" prop="srcPath" style="width: 95%">
                            <file-chooser v-model="formItems.srcPath" :disabled="disableInput"
                                          dialogType="directory"></file-chooser>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="输出路径" prop="destPath" style="width: 95%">
                            <file-chooser v-model="formItems.destPath" :disabled="disableInput"
                                          dialogType="directory"></file-chooser>
                        </Form-item>
                    </Col>
                </Row>

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
                            com/apache/hello=com/mycompany/group/myprj   延长目录；<br/>
                            com/apache/hello=com/myprj   缩短目录；<br/>
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
                        <Form-item label="执行结果" style="width: 95%">
                            <Input v-model="message" type="textarea" :autosize="{minRows: 4,maxRows: 6}"
                                   :disabled="true"></Input>
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

    var _ = require('underscore')

    var validateSet = {
        srcPath: [{type: 'string', required: true, message: '原路径不能为空', trigger: 'blur'}],
        destPath: [{type: 'string', required: true, message: '原路径不能为空', trigger: 'blur'}],
        ignoreExp: [{type: 'string', required: false, message: '原路径不能为空', trigger: 'blur'}],
        throwExp: [{type: 'string', required: false, message: '原路径不能为空', trigger: 'blur'}],
        replaceType: [{type: 'string', required: true, message: '替换类型不能为空', trigger: 'blur'}]
    };

    var defConfig = {
        srcPath: '',
        destPath: '',
        ignoreExp: '',
        throwExp: '',
        replaceExp: '',
        dirReplaceExp: '',
        replaceType: ''
    };

    export default {
        props: {
            bindId: {
                type: String,
                default: 'autoCodeReplaceUtil'
            }
        },
        data: function () {
            return {
                formItems: _.clone(this.defConfig),
                formRules: validateSet,
                constants,
                disableInput: false,
                loading: false,
                message: ''
            };
        },
        mounted() {
            var _this = this;
            let promise = this.$store.dispatch(constants.types.GET_BIND_RESOURCE, {bindId: this.bindId});
            promise.then((data) => {
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
                this.$refs['formItems'].validate((valid) => {
                    if (!valid) {
                        this.$Message.error({
                            content: '参数有误，请检查...',
                            duration: 5
                        })
                        return false;
                    }
                    var datas = requestUtils.serializeObject(_.clone(this.formItems), true, true);
                    requestUtils.postSubmit(this, constants.urls.autoCode.replace.execute, datas, function (data) {
                        this.$Message.success({
                            content: '执行成功',
                            duration: 5
                        });
                        this.message = data['message']
                    }, function (data) {
                        this.message = data['message']
                    }, true);
                });
            }
        },
        components: {
            dictSelect,
            fileChooser,
            dictRadio
        }
    };
</script>
