<template>
    <div>
        <Card>
            <p slot="title">
                <Icon type="soup-can"></Icon>
                全局配置信息
            </p>

            <Collapse :value="[1]">
                <Panel name="1">
                    MAVEN仓库配置(不使用maven仓库可以不配置)
                    <p slot="content">
                       <maven-config></maven-config>
                    </p>
                </Panel>

            </Collapse>
        </Card>
    </div>
</template>

<script>
    import mavenConfig from './globalConfigs/mavenConfig';
    import constants from '@/constants/constants';
    var _ = require('underscore');

    export default {
        name: 'globalConfiguration',
        data: function () {
            return {
                projectConfigList: []
            };
        },
        components: {
            mavenConfig
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
        methods: {
            setDefaultProject(project) {
                if (this.$store) {
                    var _this = this;
                    let promise = this.$store.dispatch(constants.types.LOAD_DEFAULT_PROJECT_CONFIG, {project: project});
                    promise.then((data) => {
                        _this.setProjectConfigs(data);
                    });
                }
            }
        }
    }
    ;
</script>