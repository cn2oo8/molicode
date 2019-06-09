<template>
    <div>
        <i-switch v-model="chooserAll" @on-change="chooserAllChange">
        </i-switch>
        全选

        &nbsp;&nbsp;
        <Button @click="fetchTemplateList" :loading="loading" size="small">
            <Icon type="refresh"></Icon>
            刷新模板列表
        </Button> &nbsp;


        <CheckboxGroup v-model="templateCheckedIdList">
            <template v-for="item in templateList">
                <Col span="6">
                    <Checkbox :label="item.id">{{item.name}}</Checkbox>
                    <Button size="small" type="default" @click="about(item)">?</Button>
                </Col>
            </template>
        </CheckboxGroup>

        <template-info ref="templateInfo"></template-info>

    </div>
</template>

<script>
    import constants from '@/constants/constants';
    import * as configUtil from '@/libs/configUtil.js';
    import templateInfo from './TemplateInfo';

    var _ = require('underscore')

    export default {
        props: {
            flushMaven: {
                type: String,
                default: '2'
            },
            sid: {
                type: String,
                default: ''
            }
        },
        data() {
            return {
                templateList: [],
                templateCheckedIdList: [],
                chooserAll: true,
                loading: false
            };
        },
        computed: {
            storeAutoXmlPath: function () {
                return this.$store.getters.getConfigObjectVal(constants.bizKeys.configs.pathConfig.configKey,
                    constants.bizKeys.configs.pathConfig.autoXmlPath);
            }
        },
        watch: {
            storeAutoXmlPath: function (newVal) {
                this.fetchTemplateList();
            }
        },
        mounted() {
            if (this.storeAutoXmlPath) {
                this.fetchTemplateList();
            }
        },
        methods: {
            chooserAllChange(choose) {
                if (choose) {
                    var allList = [];
                    for (var i = 0; i < this.templateList.length; i++) {
                        allList.push(this.templateList[i].id);
                    }
                    this.templateCheckedIdList = allList;
                } else {
                    this.templateCheckedIdList = [];
                }
            },
            fetchTemplateList() {
                var configList = this.$store.state.autoCode.defaultProjectConfig;
                var configObject = configUtil.getConfigObject(configList, constants.bizKeys.configs.pathConfig.configKey);
                var datas = {
                    autoXmlPath: configObject[constants.bizKeys.configs.pathConfig.autoXmlPath],
                    templateBaseDir: configObject[constants.bizKeys.configs.pathConfig.templateBaseDir],
                    projectKey: this.$store.state.autoCode.defaultProjectKey,
                    flushMaven: this.flushMaven,
                    sid: this.sid
                }
                if (!datas.templateBaseDir && configObject['templateType'] === 'local') {
                    this.$Message.error({
                        content: '模板基础目录尚未配置',
                        duration: 5
                    });
                    return;
                }
                if (!datas.projectKey) {
                    this.$Message.error({
                        content: '默认项目还未设置',
                        duration: 5
                    });
                    return;
                }
                var _this = this;
                let promise = this.$store.dispatch(constants.types.LOAD_AUTOMAKE_DEF_PRJ, {
                    _vue: _this,
                    sid: _this.sid,
                    flushMaven: this.flushMaven
                });
                promise.then((data) => {
                    if (data) {
                        _this.templateList = data.templates;
                        _this.chooserAllChange(_this.chooserAll);
                    }
                }).catch(function (data) {
                    var sMessage = data['message']
                    if (!sMessage || sMessage === '') {
                        sMessage = '处理异常，请联系管理员！';
                    }
                    _this.$Message.error({
                        content: sMessage,
                        duration: 10
                    });
                });
                this.$emit('fetchTemplateList');
            },
            getChoosedTemplateIds() {
                return this.templateCheckedIdList;
            },
            about(item) {
                this.$refs['templateInfo'].show(item);
            }
        },
        components: {
            templateInfo
        }
    }
</script>

<style scoped>

</style>
