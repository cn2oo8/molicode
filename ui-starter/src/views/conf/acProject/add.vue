<template>
    <div>
        <Button type="primary"  @click="showModal = true"><Icon type="plus"></Icon> 新增 项目</Button>
        <Modal v-model="showModal"
               title="新增项目"
               @on-ok="save"
               @on-cancel="cancel">

            <Form ref="formItems" :model="formItems" :rules="formRules" :label-width="120" inline>


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
                            <Input v-model="formItems.remark" :maxlength="500" type="textarea" :autosize="{minRows: 2,maxRows: 5}" :disabled="disableInput"></Input>
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

    var validateSet = {
        name: [{type: 'string', required: true, message: '名称不能为空', trigger: 'blur'}],
        remark: [{type: 'string', required: false, message: '备注不能为空', trigger: 'blur'}],
        type: [{type: 'integer', required: false, message: '类型不能为空', trigger: 'blur'}],
        creator: [{type: 'string', required: false, message: '创建人不能为空', trigger: 'blur'}]
    };

    var formItemsTemp = {
        name: null,
        remark: null,
        projectKey: null,
        type: 1,
        creator: null
    }

    export default {
        data() {
            return {
                formItems: formItemsTemp,
                formRules: validateSet,
                // formRules: constants.rules.acProject.add,
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
                    requestUtils.postSubmit(this, constants.urls.conf.acProject.add, this.formItems, function (data) {
                        this.$Message.success({
                            content: '新增成功',
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
            }
        }
    }
</script>