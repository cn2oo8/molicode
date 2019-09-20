<template>
    <div>
        <Button type="primary" @click="showModal = true">
            <Icon type="plus"></Icon>
            新增 用户信息
        </Button>

        <Modal v-model="showModal"
               title="新增用户信息"
               @on-ok="save"
               :mask-closable="false"
               @on-cancel="cancel">

            <Form ref="formItems" :model="formItems" :rules="formRules" :label-width="120" inline>


                <Row>
                    <Col span="24">
                        <Form-item label="用户名称" prop="userName" style="width: 90%">
                            <Input v-model="formItems.userName" :maxlength="30" :disabled="disableInput"></Input>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="密码" prop="password" style="width: 90%">
                            <Input v-model="formItems.password" :maxlength="30" :disabled="disableInput" type="password"></Input>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="角色码" prop="roleCode" style="width: 90%">
                            <dict-select v-model="formItems.roleCode"
                                        :kind="this.constants.dicts.dictKinds.ROlE_CODE_DICT"
                                        :clearable="false"></dict-select>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="用户昵称" prop="nickName" style="width: 90%">
                            <Input v-model="formItems.nickName" :maxlength="30" :disabled="disableInput"></Input>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="性别" prop="gender" style="width: 90%">
                            <dict-radio v-model="formItems.gender"
                                        :kind="this.constants.dicts.dictKinds.STD_GENDER"
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
                        <Form-item label="备注" prop="remark" style="width: 90%">
                            <Input v-model="formItems.remark" :maxlength="200" :disabled="disableInput"></Input>
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

    var validateSet = {
        userName: [{type: 'string', required: true, message: '用户名称不能为空', trigger: 'blur'}],
        nickName: [{type: 'string', required: false, message: '用户昵称不能为空', trigger: 'blur'}],
        password: [{type: 'string', required: true, message: '密码不能为空', trigger: 'blur'}],
        roleCode: [{type: 'string', required: true, message: '角色码不能为空', trigger: 'blur'}],
        remark: [{type: 'string', required: false, message: '备注不能为空', trigger: 'blur'}]
    };

    var formItems = {
        userName: null,
        nickName: null,
        gender: 1,
        password: null,
        birthDay: null,
        userMark: null,
        roleCode: 'normal_user',
        remark: null
    };

    export default {
        data() {
            return {
                formItems: formItems,
                formRules: validateSet,
                // formRules: constants.rules.acUser.add,
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
                    requestUtils.postSubmit(this, constants.urls.sys.acUser.add, this.formItems, function (data) {
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
        },
        components: {
            dictSelect,
            dictRadio
        }
    }
</script>
