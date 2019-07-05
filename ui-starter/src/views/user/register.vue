<template>
    <div>
        <Modal v-model="showModal"
               title="用户注册" width="500">

            <Form ref="loginForm" :model="formItems" :rules="rules" :label-width="120" inline>

                <Row>
                    <Col span="24">
                        <Form-item label="用户名" style="width: 96%" prop="userName">
                            <Input v-model="formItems.userName" placeholder="请输入用户名"></Input>
                        </Form-item>
                    </Col>
                </Row>


                <Row>
                    <Col span="24">
                        <Form-item label="密码" style="width: 96%" prop="password">
                            <Input v-model="formItems.password" placeholder="请输入的密码,最少6位" type="password"></Input>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="确认密码" style="width: 96%" prop="rePass">
                            <Input v-model="formItems.rePass" placeholder="请再次输入的密码,和上个密码一致" type="password"></Input>
                        </Form-item>
                    </Col>
                </Row>


                <Row>
                    <Col span="24">
                        <Form-item label="性别" style="width: 96%" prop="gender">
                            <dict-radio v-model="formItems.gender"
                                        :number="true" :kind="this.constants.dicts.dictKinds.STD_GENDER"></dict-radio>
                        </Form-item>
                    </Col>
                </Row>


                <Row>
                    <Col span="24">
                        <Form-item label="昵称" style="width: 96%" prop="nickName">
                            <Input v-model="formItems.nickName" placeholder="请输入昵称"></Input>
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="公司" style="width: 96%" prop="remark">
                            <Input v-model="formItems.remark" placeholder="请输入公司名称"></Input>
                        </Form-item>
                    </Col>
                </Row>


            </Form>

            <div slot="footer">
                <Button type="info" :loading="loading" @click="doRegister">
                    注册
                </Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    import dictRadio from '@/views/common/dict/DictRadio';
    import constants from '@/constants/constants';
    import requestUtils from '@/request/requestUtils.js'

    export default {
        name: 'register',
        data() {
            const valideRePassword = (rule, value, callback) => {
                if (value !== this.formItems.password) {
                    callback(new Error('两次输入密码不一致'));
                } else {
                    callback();
                }
            };
            return {
                showModal: false,
                loading: false,
                constants,
                formItems: {
                    userName: '',
                    password: '',
                    rePass: '',
                    gender: 1,
                    remark: '',
                    birthDay: ''
                },
                rules: {
                    userName: [{type: 'string', required: true, message: '用户名不能为空', trigger: 'blur'}],
                    password: [
                        {type: 'string', required: true, message: '密码不能为空', trigger: 'blur'},
                        {min: 6, message: '请至少输入6个字符', trigger: 'blur'},
                        {max: 32, message: '最多输入32个字符', trigger: 'blur'}
                    ],
                    rePass: [
                        {type: 'string', required: true, message: '确认密码不能为空', trigger: 'blur'},
                        {validator: valideRePassword, trigger: 'blur'}
                    ]
                }
            };
        },
        methods: {
            show(flag) {
                this.showModal = flag;
            },
            doRegister() {
                this.$refs['loginForm'].validate((valid) => {
                    if (!valid) {
                        return false;
                    }
                    var datas = requestUtils.serializeObject(this.formItems, true, true);
                    requestUtils.postSubmit(this, constants.urls.loginfree.login.register, datas, function (data) {
                        this.$Message.success({
                            content: '注册成功，请输入用户名&密码进行登录！',
                            duration: 3
                        })
                        this.showModal = false
                    });
                }, null, true);
            }
        },
        components: {
            dictRadio
        }
    };
</script>

<style scoped>

</style>
