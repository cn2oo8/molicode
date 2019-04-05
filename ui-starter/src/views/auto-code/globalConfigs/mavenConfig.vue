<template>
    <div>
        <Form ref="formItems" :model="formItems" :rules="formRules" :label-width="120" inline>
            <Row>
                <Col span="3" style="text-align: center">
                    说明：
                </Col>
                <Col span="20" style="font-size: smaller">
                       因为模板是通过maven仓库进行托管，所以需要配置一下maven相关的环境信息。<br/>
                        主要有： 本地仓库，maven_home， 自定义setting(主要是自定义仓库，如果用公共仓库，可以不配置)；
                    JAVA_HOME 可选，如果环境变量未配置，请配置JAVA_HOME参数。
                    <br/>
                </Col>
            </Row>


            <Row>
                <Col span="24">
                    <Form-item label="本地仓库目录" prop="localRepository" style="width: 80%">
                        <file-chooser v-model="formItems.localRepository" :disabled="disableInput"
                                      dialogType="directory"></file-chooser>
                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="MAVEN_HOME(可选)" prop="mavenHome" style="width: 80%">
                        <file-chooser v-model="formItems.mavenHome" :disabled="disableInput"
                                      dialogType="directory"></file-chooser>
                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="自定义setting(可选)" prop="mavenSetting" style="width: 80%">
                        <file-chooser v-model="formItems.mavenSetting" :disabled="disableInput" dialogType="open"
                                      fileType="other" fileExt="*.xml"></file-chooser>
                    </Form-item>
                </Col>
            </Row>
            <Row>
                <Col span="24">
                    <Form-item label="JAVA_HOME(可选)" prop="javaHome" style="width: 60%">
                        <file-chooser v-model="formItems.javaHome" :disabled="disableInput"
                                      dialogType="directory"></file-chooser>
                    </Form-item>
                    仅在环境变量中未设置JAVA_HOME才需要配置。
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
    import requestUtils from '@/request/requestUtils.js';
    import fileChooser from '@/views/common/file/fileChooser';

    var _ = require('underscore')

    var validateSet = {
        localRepository: [{type: 'string', required: true, message: '本地仓库不能为空', trigger: 'blur'}],
        mavenHome: [{type: 'string', required: false, message: 'MAVEN_HOME格式不正确', trigger: 'blur'}],
        mavenSetting: [{type: 'string', required: false, message: 'mavenSetting格式不正确', trigger: 'blur'}],
        javaHome: [{type: 'string', required: false, message: 'JAVA_HOME格式不正确', trigger: 'blur'}]
    };

    var defConfig = {
        mavenHome: '',
        localRepository: '',
        mavenSetting: '',
        javaHome: ''
    };

    export default {
        props: {
            extKey: {
                type: String,
                default: 'mavenConfig'
            },
            configInfo: {
                type: Object,
                default: function () {
                    return defConfig;
                }
            }
        },
        computed: {
            globalConfigInfo: function () {
                return this.$store.state.autoCode.globalConfig.mavenConfig;
            }
        },
        watch: {
            globalConfigInfo: function (newVal) {
                this.formItems = newVal;
            }
        },
        data: function () {
            return {
                formItems: _.clone(this.configInfo),
                formRules: validateSet,
                constants,
                disableInput: false,
                loading: false
            };
        },
        mounted() {
            let promise = this.$store.dispatch(constants.types.LOAD_GLOBAL_CONFIG, this.getConfigData());
            var _this = this;
            promise.then((data) => {
                _this.$Message.success({
                    content: '获取配置信息成功',
                    duration: 5
                });
            });
        },
        methods: {
            save: function () {
                this.$refs['formItems'].validate((valid) => {
                    if (!valid) {
                        return false;
                    }
                    var _this = this;
                    let promise = this.$store.dispatch(constants.types.SAVE_GLOBAL_CONFIG, this.getConfigData());
                    promise.then((data) => {
                        _this.$Message.success({
                            content: '保存配置信息成功',
                            duration: 5
                        });
                    });
                });
            },
            getConfigData() {
                return {
                    extKey: this.extKey,
                    extValue: JSON.stringify(this.formItems),
                    type: 2,
                    _vue: this
                };
            }
        },
        components: {
            dictSelect,
            fileChooser
        }
    };
</script>