<template>
    <div>
        <div style="background:#eee;padding: 10px">
            <Card>
                <p slot="title">
                    <Icon type="ios-list"></Icon>
                    项目列表
                </p>
                <autoCodeProjectList v-on:setDefaultProject="setDefaultProject"></autoCodeProjectList>
            </Card>
        </div>

        <div v-show="defaultProject == null">
            <Alert type="warning" show-icon>
                警告
                <template slot="desc">
                    <span style="font-size: 13px; font-weight: normal;">
                      您尚未设置默认项目配置信息，请先创建项目并设置默认项目相关配置信息，谢谢支持！
                    </span>
                </template>
            </Alert>
        </div>
        <div style="background:#eee;padding: 10px" v-show="defaultProject != null">
            <Card>
                <p slot="title">
                    <Icon type="soup-can"></Icon>
                    默认项目下配置信息 （默认项目ID:{{defaultProjectId}}）
                </p>

                <Collapse :value="[1,2,3]">
                    <Panel name="1">
                        数据库设置
                        <p slot="content">
                            <database-config ref="databaseConfig"></database-config>
                        </p>
                    </Panel>

                    <Panel name="2">
                        工程目录设置
                        <p slot="content">
                            <path-config ref="pathConfig"></path-config>
                        </p>
                    </Panel>


                    <Panel name="3">
                        业务代码设置
                        <p slot="content">
                            <code-config ref="codeConfig"></code-config>
                        </p>
                    </Panel>

                    <Panel name="4">
                        json扩展设置(非必填)
                        <p slot="content">
                            <json-config ref="jsonConfig"></json-config>
                        </p>
                    </Panel>
                </Collapse>
            </Card>
        </div>
    </div>
</template>

<script>
    import databaseConfig from './configs/databaseConfig';
    import pathConfig from './configs/pathConfig';
    import codeConfig from './configs/codeConfig';
    import jsonConfig from './configs/jsonConfig';
    import autoCodeProjectList from '@/views/conf/acProject/autoCodeProjectList';
    import constants from '@/constants/constants';

    var _ = require('underscore')

    const configRefNames = ['databaseConfig', 'pathConfig', 'codeConfig', 'jsonConfig']

    /**
     * 从list 中查询配置信息并返回结果
     * @param configList
     * @param configKey
     * @returns {*}
     */
    function getConfigInfoByKey(configList, configKey) {
        if (_.isArray(configList) === false) {
            return {};
        }
        for (var i = 0; i < configList.length; i++) {
            var config = configList[i];
            if (config.configKey === configKey) {
                return JSON.parse(config.configValue);
            }
        }
        return {};
    }

    export default {
        name: 'configuration',
        data: function () {
            return {
                projectConfigList: []
            };
        },
        components: {
            databaseConfig,
            autoCodeProjectList,
            pathConfig,
            codeConfig,
            jsonConfig
        },
        computed: {
            defaultProjectId() {
                return this.defaultProject ? this.defaultProject.id : '未设置';
            },
            defaultProject: function () {
                return this.$store.state.autoCode.defaultProject;
            }
        },
        watch: {
            defaultProject: function (newVal) {
                this.setDefaultProject(newVal);
            }
        },
        mounted() {
            this.$store.dispatch(constants.types.LOAD_SYSTEM_PROFILE, {_vue: this});
        },
        methods: {
            setDefaultProject(project) {
                if (this.$store) {
                    var _this = this;
                    let promise = this.$store.dispatch(constants.types.LOAD_DEFAULT_PROJECT_CONFIG, {project: project});
                    promise.then((data) => {
                        _this.setProjectConfigs(data);
                    });
                }
            },
            setProjectConfigs: function (configList) {
                if (!configList) {
                    configList = [];
                }
                for (var index = 0; index < configRefNames.length; index++) {
                    var ref = this.$refs[configRefNames[index]];
                    var configInfo = getConfigInfoByKey(configList, ref.configKey)
                    ref.notifyConfigInfo(this.defaultProject.projectKey, configInfo);
                }
            }
        }
    }
    ;
</script>
