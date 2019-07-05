<template>
    <div>
        <Button @click="show(true)" type="info" size="small" v-if="showButton">
            代码预览
        </Button> &nbsp;

        <Modal v-model="showModal"
               :title="title"
               width="94%">

            <div v-if="templateResultList.length==0">
                没有文件列表，无法预览！
            </div>


            <Form :label-width="120" inline v-if="templateResultList.length > 0">
                <Row>
                    <Form-item label="文件列表" style="width: 98%">
                        <RadioGroup v-model="templateId" type="button">
                            <template v-for="result in templateResultList">
                                <Radio :label="result.id" :key="result.id">{{result.name}}</Radio>
                            </template>
                        </RadioGroup>
                    </Form-item>
                </Row>
                <Row>
                    <Col span="24">
                        <Form-item label="文件信息" prop="relativePath" style="width: 90%">
                            <Tag>模板id:</Tag>
                            {{templateId}} &nbsp;&nbsp;&nbsp;&nbsp;
                            <Tag>相对路径:</Tag>
                            {{formItems.relativePath}}
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="输出内容" prop="code" style="width: 96%">
                            <Input v-model="formItems.code" type="textarea" placeholder="请选择文件进行输出..."
                                   :autosize="{minRows:15,maxRows: 15}"></Input>
                        </Form-item>
                    </Col>
                </Row>
            </Form>

            <div slot="footer">
                <Button type="info" :loading="loading" @click="show(false)">
                    关闭
                </Button>
            </div>
        </Modal>

    </div>
</template>
<script>
    var _ = require('underscore')
    import constants from '@/constants/constants';
    import requestUtils from '@/request/requestUtils.js';

    export default {
        name: 'codePreview',
        props: {
            showButton: {
                type: Boolean,
                required: false,
                default: false
            },
            item: {
                type: Object,
                required: true
            }
        },
        computed: {
            templateResultList() {
                if (this.item['templateResultList']) {
                    return this.item['templateResultList'];
                }
                return [];
            }
        },
        watch: {
            templateId: function (newVal, oldVal) {
                this.templateResultChange(newVal);
            }
        },
        data() {
            return {
                templateId: null,
                loading: false,
                showModal: false,
                title: '代码预览',
                formItems: {
                    relativePath: '',
                    code: ''
                }
            };
        },
        methods: {
            show(flag) {
                this.showModal = flag;
            },
            templateResultChange: function (newVal) {
                let templateResult = _.find(this.templateResultList, function (val) {
                    return val.id === newVal;
                });
                if (templateResult === null || templateResult === undefined) {
                    return;
                }
                this.formItems.relativePath = templateResult.relativePath;
                if (templateResult['renderedContent'] !== null && templateResult['renderedContent'] !== undefined) {
                    this.formItems.code = templateResult['renderedContent'];
                    return;
                }

                this.formItems.code = '数据加载中...请稍候!';
                let params = {
                    projectKey: this.item['projectKey'],
                    outputDir: this.item['outputDir'],
                    relativePath: this.formItems.relativePath
                }
                requestUtils.postSubmit(this, constants.urls.common.file.loadProjectOutputFile, params, function (data) {
                    templateResult['renderedContent'] = data['value'];
                    this.formItems.code = templateResult['renderedContent'];
                }, null, true);
            }
        }
    };
</script>

<style scoped>

</style>
