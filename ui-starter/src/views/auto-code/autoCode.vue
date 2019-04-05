<template>
    <div>
        <div v-show="!hasDefaultProject">
            <Alert type="warning" show-icon>
                警告
                <template slot="desc">
                    <span style="font-size: 13px; font-weight: normal;">
                      您尚未设置默认项目配置信息，将无法使用自动代码功能,请先创建项目并设置默认项目信息。
                    </span>
                </template>
            </Alert>
        </div>

        <div style="background:#eee;padding: 10px" v-show="hasDefaultProject">
            <Card>
                <p slot="title">
                    <Icon type="ios-list"></Icon>
                    数据库配置信息
                </p>
                待完善
            </Card>


            <Card>
                <p slot="title">
                    <Icon type="ios-list"></Icon>
                    表模型（TableModel.xml）生成
                </p>
                <table-model></table-model>
            </Card>


            <Card>
                <p slot="title">
                    <Icon type="ios-list"></Icon>
                    业务代码配置
                </p>
                待完善
            </Card>


            <Card>
                <p slot="title">
                    <Icon type="ios-list"></Icon>
                    代码生成
                </p>
                <coder></coder>
            </Card>
        </div>
    </div>
</template>

<script>
    import databaseConfig from './configs/databaseConfig';
    import pathConfig from './configs/pathConfig';
    import codeConfig from './configs/codeConfig';
    import requestUtils from '@/request/requestUtils.js';
    import constants from '@/constants/constants';
    import tableModel from './coding/tableModel';
    import coder from './coding/coder';

    var _ = require('underscore');

    export default {
        name: 'autoCode',
        components: {
            tableModel,
            coder
        },
        beforeMount: function () {
            this.$store.dispatch(constants.types.LOAD_DEFAULT_PROJECT, {loadConfig: true});
        },
        computed: {
            hasDefaultProject: function () {
                return this.$store.state.autoCode.defaultProjectKey !== null;
            }
        }
    };
</script>

<style scoped>

</style>