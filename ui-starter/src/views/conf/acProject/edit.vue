<template>
    <div>
        <Modal v-model="showModal"
               title="修改项目"
               @on-ok="save"
               :mask-closable="false"
               @on-cancel="cancel">
            <Form ref="formItems" :model="formItems" :rules="formRules" :label-width="120" inline>


                <Row>
                    <Col span="24">
                        <Form-item label="id" prop="id" style="width: 90%">
                            <Input v-model="formItems.id" :maxlength="10" :disabled="true"></Input>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="名称" prop="name" style="width: 90%">
                            <Input v-model="formItems.name" :maxlength="50" :disabled="disableInput"></Input>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="备注" prop="remark" style="width: 90%">
                            <Input v-model="formItems.remark" :maxlength="500" type="textarea" :autosize="{minRows: 2,maxRows: 5}"  :disabled="disableInput"></Input>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="项目key" prop="projectKey" style="width: 90%">
                            <Input v-model="formItems.projectKey" :maxlength="50" :disabled="true"></Input>
                        </Form-item>
                    </Col>
                </Row>


            </Form>
            <div slot="footer">
                <Button type="default" @click="cancel">取消</Button>
                <Button type="primary" @click="save" :loading="loading"><Icon type="android-done"></Icon> 保存</Button>
            </div>
        </Modal>

    </div>
</template>

<script>
    import constants from '@/constants/constants.js'
    import requestUtils from '@/request/requestUtils.js'
    var _ = require('underscore')

    var validateSet = {
        name: [{type: 'string', required: true, message: '名称不能为空', trigger: 'blur'}],
        remark: [{type: 'string', required: false, message: '备注不能为空', trigger: 'blur'}],
        projectKey: [{type: 'string', required: false, message: '项目key不能为空', trigger: 'blur'}],
        operator: [{type: 'string', required: false, message: '修改人不能为空', trigger: 'blur'}]
    };
    export default {
        data() {
            return {
                formItems: {},
                formRules: validateSet,
                // formRules: constants.rules.acProject.edit,
                showModal: false,
                loading: false,
                disableInput: false
            }
        },

        methods: {
            save: function () {
                this.$refs['formItems'].validate((valid) => {
                    if (!valid) {
                        return false
                    }
                    requestUtils.postSubmit(this, constants.urls.conf.acProject.update, this.formItems, function (data) {
                        this.$Message.success({
                            content: '修改成功',
                            duration: 3
                        })
                        this.showModal = false
                        this.$emit(constants.actions.common.refreshList)
                    })
                })
            },
            'cancel': function () {
                this.$refs['formItems'].resetFields()
                this.showModal = false
            },
            'show': function (isShow) {
                this.showModal = isShow
            },
            'editItem': function (item) {
                this.formItems = _.clone(item)
                this.show(true)
            }
        }
    }
</script>
