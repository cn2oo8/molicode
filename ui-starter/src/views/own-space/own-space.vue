<style lang="less">
    @import './own-space.less';
</style>

<template>
    <div>
        <Card>
            <p slot="title">
                <Icon type="person"></Icon>
                个人信息
            </p>
            <div>
                <Form
                        ref="userForm"
                        :model="userForm"
                        :label-width="100"
                        label-position="right"
                        :rules="inforValidate"
                >
                    <FormItem label="用户名：" prop="userName">
                        <div style="display:inline-block;width:300px;">
                            {{userForm.userName}}
                        </div>
                    </FormItem>

                    <FormItem label="用户昵称：" prop="nickName">
                        <div style="display:inline-block;width:300px;">
                            <Input v-model="userForm.nickName"></Input>
                        </div>
                    </FormItem>

                    <FormItem label="性别：" prop="gender">
                        <div style="display:inline-block;width:300px;">
                            <dict-radio v-model="userForm.gender"
                                        :number="true" :kind="this.constants.dicts.dictKinds.STD_GENDER"></dict-radio>
                        </div>
                    </FormItem>


                    <FormItem label="公司：" prop="remark">
                        <div style="display:inline-block;width:300px;">
                            <Input v-model="userForm.remark"></Input>
                        </div>
                    </FormItem>

                    <FormItem label="生日：" prop="birthDay">
                        <div style="display:inline-block;width:300px;">
                            <DatePicker v-model="userForm.birthDay" type="date" placeholder="生日"
                                        style="width: 200px"></DatePicker>
                        </div>
                    </FormItem>

                    <FormItem label="角色码：">
                        <span>{{ userForm.roleCode }}</span>
                    </FormItem>
                    <FormItem label="登录密码：">
                        <Button type="text" size="small" @click="showEditPassword">修改密码</Button>
                    </FormItem>
                    <div>
                        <Button type="text" style="width: 100px;" @click="cancelEditUserInfo">取消</Button>
                        <Button type="primary" style="width: 100px;" :loading="save_loading" @click="saveEdit">保存
                        </Button>
                    </div>
                </Form>
            </div>
        </Card>
        <Modal v-model="editPasswordModal" :closable='false' :mask-closable=false :width="500">
            <h3 slot="header" style="color:#2D8CF0">修改密码</h3>
            <Form ref="editPasswordForm" :model="editPasswordForm" :label-width="100" label-position="right"
                  :rules="passwordValidate">
                <FormItem label="原密码" prop="oldPass" :error="oldPassError">
                    <Input v-model="editPasswordForm.oldPass" placeholder="请输入现在使用的密码" type="password"></Input>
                </FormItem>
                <FormItem label="新密码" prop="newPass">
                    <Input v-model="editPasswordForm.newPass" placeholder="请输入新密码，至少6位字符" type="password"></Input>
                </FormItem>
                <FormItem label="确认新密码" prop="rePass">
                    <Input v-model="editPasswordForm.rePass" placeholder="请再次输入新密码" type="password"></Input>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="text" @click="cancelEditPass">取消</Button>
                <Button type="primary" :loading="savePassLoading" @click="saveEditPass">保存</Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    import dictRadio from '@/views/common/dict/DictRadio';
    import constants from '@/constants/constants';
    import requestUtils from '@/request/requestUtils.js';

    export default {
        name: 'ownspace_index',
        data() {
            const valideRePassword = (rule, value, callback) => {
                if (value !== this.editPasswordForm.newPass) {
                    callback(new Error('两次输入密码不一致'));
                } else {
                    callback();
                }
            };
            return {
                constants,
                userForm: {
                    userName: '',
                    nickName: '',
                    roleCode: '',
                    gender: 1,
                    remark: '',
                    birthDay: null
                },
                save_loading: false,
                editPasswordModal: false, // 修改密码模态框显示
                savePassLoading: false,
                oldPassError: '',
                inforValidate: {
                    nickName: [
                        {required: true, message: '请输入昵称', trigger: 'blur'}
                    ]
                },
                editPasswordForm: {
                    oldPass: '',
                    newPass: '',
                    rePass: ''
                },
                passwordValidate: {
                    oldPass: [
                        {required: true, message: '请输入原密码', trigger: 'blur'}
                    ],
                    newPass: [
                        {required: true, message: '请输入新密码', trigger: 'blur'},
                        {min: 6, message: '请至少输入6个字符', trigger: 'blur'},
                        {max: 32, message: '最多输入32个字符', trigger: 'blur'}
                    ],
                    rePass: [
                        {required: true, message: '请再次输入新密码', trigger: 'blur'},
                        {validator: valideRePassword, trigger: 'blur'}
                    ]
                }
            };
        },
        computed: {
            userInfo: function () {
                return this.$store.state.user.userInfo;
            }
        },
        watch: {
            'userInfo': function (newVal) {
                this.init(newVal);
            }
        },
        methods: {
            showEditPassword() {
                this.editPasswordForm.oldPass = '';
                this.editPasswordForm.newPass = '';
                this.editPasswordForm.rePass = '';
                this.editPasswordModal = true;
            },
            cancelEditUserInfo() {
                this.$store.commit('removeTag', 'ownspace_index');
                localStorage.pageOpenedList = JSON.stringify(this.$store.state.app.pageOpenedList);
                let lastPageName = '';
                if (this.$store.state.app.pageOpenedList.length > 1) {
                    lastPageName = this.$store.state.app.pageOpenedList[1].name;
                } else {
                    lastPageName = this.$store.state.app.pageOpenedList[0].name;
                }
                this.$router.push({
                    name: lastPageName
                });
            },
            saveEdit() {
                this.$refs['userForm'].validate((valid) => {
                    if (valid) {
                        this.saveInfoAjax();
                    }
                });
            },
            cancelEditPass() {
                this.editPasswordModal = false;
            },
            saveEditPass() {
                let _this = this;
                this.$refs['editPasswordForm'].validate((valid) => {
                    if (valid) {
                        // this.savePassLoading = true;
                        // you can write ajax request here
                        var datas = requestUtils.serializeObject(this.editPasswordForm, true, true);
                        let promise = this.$store.dispatch(constants.types.CHANGE_PASSWORD, {
                            'data': datas,
                            '_vue': this,
                            'loadingKey': 'savePassLoading'
                        });
                        promise.then(function (data) {
                            _this.cancelEditPass();
                            _this.$Message.success('修改成功');
                        });
                    }
                });
            },
            init(userInfo) {
                if (userInfo === null || userInfo === undefined) {
                    userInfo = this.$store.state.user.userInfo;
                }
                if (userInfo !== undefined && userInfo !== null) {
                    this.userForm.userName = userInfo.userName;
                    this.userForm.nickName = userInfo.nickName;
                    this.userForm.roleCode = userInfo.roleCode;
                    this.userForm.gender = userInfo.gender;
                    this.userForm.remark = userInfo.remark;
                    if (userInfo.birthDay) {
                        this.userForm.birthDay = new Date(userInfo.birthDay);
                    }
                }
            },
            saveInfoAjax() {
                var datas = requestUtils.serializeObject(this.userForm, true, true);
                requestUtils.postSubmit(this, constants.urls.sys.acUser.updateUserInfo, datas, function (data) {
                    this.$Message.success('保存成功');
                    this.$store.commit(constants.types.SET_LOGIN_USER, {userInfo: data.value});

                }, null, 'save_loading');
            }
        },
        mounted() {
            this.init();
        },
        components: {
            dictRadio
        }
    };
</script>
