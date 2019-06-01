<template>
    <div>
        <Form ref="formItems" :model="formItems" :rules="formRules" :label-width="120" inline>
            <Row>
                <Col span="3" style="text-align: center">
                    说明：
                </Col>
                <Col span="20" style="font-size: smaller">
                   代码相关参数配置，比较基本的几个参数：<br/>
                    author：可以通过 ${config.author} 获取<br/>
                    basePackage: 可以通过 ${config.basePackage} 获取, 主要决定代码的输出目录。<br/>
                    artifactId: 可通过 ${config.artifactId} 获取，一般内容比较使用，主要是用于模块文件的前缀；<br/>
                    category(应用子包名): 可通过 ${config.category} 获取，用于类的子分包；<br/>

                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="作者(author)" prop="author" style="width: 95%">
                        <Input v-model="formItems.author" :maxlength="50" :disabled="disableInput"></Input>
                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="基础包路径(basePackage)" prop="basePackage" style="width: 95%">
                        <Input v-model="formItems.basePackage" :maxlength="200" :disabled="disableInput"></Input>
                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="artifactId(前置)" prop="artifactId" style="width: 95%">
                        <Input v-model="formItems.artifactId" :maxlength="200" :disabled="disableInput"></Input>
                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="应用子包名" prop="category" style="width: 95%">
                        <Input v-model="formItems.category" :maxlength="200" :disabled="disableInput"></Input>
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
        author: [{type: 'string', required: true, message: '作者不能为空', trigger: 'blur'}],
        basePackage: [{type: 'string', required: true, message: '基础包路径不能为空', trigger: 'blur'}],
        artifactId: [{type: 'string', required: true, message: 'maven artifactId前置不能为空', trigger: 'blur'}]
    };

    var defConfig = {
        author: 'MoliCode',
        basePackage: '',
        artifactId: '',
        category: ''
    };

    export default {
        props: {
            defaultProjectKey: {
                type: String
            },
            configKey: {
                type: String,
                default: 'codeConfig'
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
