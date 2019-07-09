<template>
    <div>
        <Modal v-model="showModal"
               title="修改用户信息"
               @on-ok="save"
               @on-cancel="cancel">
            <Form ref="formItems" :model="formItems" :rules="formRules" :label-width="120" inline>


                <Row>
                    <Col span="24">
                        <Form-item label="Id" prop="id" style="width: 90%">
                            <Input v-model="formItems.id" :maxlength="19" :disabled="true"></Input>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="用户名称" prop="userName" style="width: 90%">
                            <Input v-model="formItems.userName" :maxlength="50" :disabled="true"></Input>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="用户昵称" prop="nickName" style="width: 90%">
                            <Input v-model="formItems.nickName" :maxlength="50" :disabled="disableInput"></Input>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="性别" prop="gender" style="width: 90%">
                            <dict-radio v-model="formItems.gender"
                                        :kind="this.constants.dicts.dictKinds.STD_GENDER"
                                        :numer="true"
                                        :clearable="false"></dict-radio>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="出生日期" prop="birthDay" style="width: 90%">
                            <DatePicker v-model="formItems.birthDay" type="date" placeholder="生日"
                                        style="width: 200px"></DatePicker>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="角色码" prop="roleCode" style="width: 90%">
                            <Input v-model="formItems.roleCode" :maxlength="50" :disabled="disableInput"></Input>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="备注" prop="remark" style="width: 90%">
                            <Input v-model="formItems.remark" :maxlength="200" :disabled="disableInput"></Input>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="状态" prop="status" style="width: 90%">
                            <dict-select v-model="formItems.status"
                                         :kind="this.constants.dicts.dictKinds.STD_STATUS"
                                         :disabled="disableInput"></dict-select>
                        </Form-item>
                    </Col>
                </Row>

            </Form>
            <div slot="footer">
                <Button type="default" @click="cancel">取消</Button>
                <Button type="primary" @click="save" :loading="loading">
                    <Icon type="android-done"></Icon>
                    保存
                </Button>
            </div>
        </Modal>

    </div>
</template>

<script>
    import constants from '@/constants/constants.js'
    import requestUtils from '@/request/requestUtils.js'
    import dictRadio from '@/views/common/dict/DictRadio'
    import dictSelect from '@/views/common/dict/DictSelect'

    var _ = require('underscore')

    var validateSet = {
        userName: [{type: 'string', required: true, message: '用户名称不能为空', trigger: 'blur'}],
        nickName: [{type: 'string', required: false, message: '用户昵称不能为空', trigger: 'blur'}],
        roleCode: [{type: 'string', required: false, message: '角色码不能为空', trigger: 'blur'}],
        remark: [{type: 'string', required: false, message: '备注不能为空', trigger: 'blur'}]
    };
    export default {
        data() {
            return {
                formItems: {},
                formRules: validateSet,
                showModal: false,
                loading: false,
                disableInput: false,
                constants
            }
        },

        methods: {
            save: function () {
                this.$refs['formItems'].validate((valid) => {
                    if (!valid) {
                        return false
                    }
                    requestUtils.postSubmit(this, constants.urls.sys.acUser.update, this.formItems, function (data) {
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
                let itemClone = _.clone(item);
                if (itemClone['birthDay']) {
                    itemClone['birthDay'] = new Date(itemClone['birthDay']);
                }
                this.formItems = itemClone;
                this.show(true)
            }
        },
        components: {
            dictRadio,
            dictSelect
        }
    }
</script>
