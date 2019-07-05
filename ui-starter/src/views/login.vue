<style lang="less">
    @import './login.less';
</style>

<template>
    <div class="login" @keydown.enter="handleSubmit">

        <div style="padding-left: 5%; padding-top: 20%; width: 100%;height: 100%">
            <h1 style="color:#cecece;font-family: Georgia; ">MoliCode，Tech make life better</h1>
        </div>
        <div class="login-con">
            <Card :bordered="false">
                <p slot="title">
                    <Icon type="log-in"></Icon>
                    欢迎登录MoliCode
                </p>
                <div class="form-con">
                    <Form ref="loginForm" :model="form" :rules="rules">
                        <FormItem prop="userName">
                            <Input v-model="form.userName" placeholder="请输入用户名">
                            <span slot="prepend">
                                    <Icon :size="16" type="person"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem prop="password">
                            <Input type="password" v-model="form.password" placeholder="请输入密码">
                            <span slot="prepend">
                                    <Icon :size="14" type="locked"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem>
                            <Button @click="handleSubmit" type="primary" long :loading="loading">登录</Button>
                        </FormItem>
                    </Form>
                    <p class="login-tip">输入任意用户名和密码即可</p>
                </div>
            </Card>
        </div>
    </div>
</template>

<script>
    import constants from '@/constants/constants';
    import Cookies from 'js-cookie';
    import requestUtils from '@/request/requestUtils.js';

    var _ = require('underscore');

    export default {
        data() {
            return {
                form: {
                    userName: 'admin',
                    password: ''
                },
                rules: {
                    userName: [
                        {required: true, message: '账号不能为空', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '密码不能为空', trigger: 'blur'}
                    ]
                },
                loading: false
            };
        },
        methods: {
            handleSubmit() {
                let _this = this;
                this.$refs.loginForm.validate((valid) => {
                    if (!valid) {
                        return;
                    }
                    var datas = requestUtils.serializeObject(_this.form, true, true);
                    requestUtils.postSubmit(_this, constants.urls.loginfree.login.login, datas, function (data) {
                        this.$store.commit('setAvator', 'https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3448484253,3685836170&fm=27&gp=0.jpg');
                        this.$store.commit('setLoginUser', {'userInfo': data.value, vm: this});
                        this.$router.push({
                            name: 'home_index'
                        });
                        _this.$Message.success({
                            content: '登录成功',
                            duration: 10
                        });
                    }, function (data) {
                        var sMessage = data['message']
                        if (!sMessage || sMessage === '') {
                            sMessage = '处理异常，请联系管理员！'
                        }
                        _this.$Message.error({
                            content: sMessage,
                            duration: 10
                        });
                    });
                }, true);
            }
        }
    };
</script>

<style>

</style>
