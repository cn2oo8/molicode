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
                    代码生成
                </p>
                <coder></coder>
            </Card>
        </div>
    </div>
</template>

<script>
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
