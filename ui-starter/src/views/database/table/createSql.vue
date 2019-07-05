<template>
    <div>
        <Button @click="show(true)" type="info" size="small" v-if="showButton">
            CREATE SQL转换
        </Button> &nbsp;

        <Modal v-model="showModal"
               :title="title"
               width="94%">

            <Form :label-width="120" inline>
                <Row>
                    <Col span="24">
                        <Form-item label="createSQL" prop="sql" style="width: 90%">
                            <Input v-model="formItems.sql" type="textarea" placeholder="请输入建表语句（目前仅MySQL支持）"
                                   :autosize="{minRows:10,maxRows: 10}"></Input>
                        </Form-item>
                    </Col>
                </Row>
            </Form>

            <div slot="footer">
                <Button type="default" :loading="loading" @click="show(false)">
                    取消
                </Button>


                <Button type="primary" :loading="loading" @click="execute">
                    <Icon type="play"></Icon>
                    分析转换CreateSQL
                </Button>
            </div>
        </Modal>

    </div>
</template>

<script>
    import constants from '@/constants/constants';
    import requestUtils from '@/request/requestUtils.js';

    var _ = require('underscore')

    export default {
        name: 'createSql',
        props: {
            showButton: {
                type: Boolean,
                required: false,
                default: false
            }
        },
        data() {
            return {
                loading: false,
                showModal: false,
                title: 'create sql转换',
                formItems: {
                    sql: ''
                }
            };
        },
        methods: {
            show(isShow) {
                this.showModal = isShow;
                if (!isShow) {
                    this.formItems.sql = '';
                }
            },
            execute() {
                var projectKey = this.$store.state.autoCode.defaultProjectKey;
                if (!projectKey) {
                    this.$Message.error({
                        content: '默认项目尚未配置',
                        duration: 5
                    });
                    return;
                }
                let params = {
                    projectKey,
                    'createSql': this.formItems.sql
                };
                requestUtils.postSubmit(this, constants.urls.autoCode.tableModel.getTableListBySql, params, function (data) {
                    if (data.value && data.value.length > 0) {
                        this.$emit(constants.actions.autoCode.sqlTableListNotify, data.value);
                        this.show(false);
                    } else {
                        this.$Message.error({
                            content: '解析SQL失败，请检查！',
                            duration: 5
                        });
                    }
                }, null, true);
            }
        }
    };
</script>

<style scoped>

</style>
