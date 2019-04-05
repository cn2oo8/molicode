<template>
    <div>
        <Button @click="fetchTableList" type="info">
            <Icon type="arrow-down-a"></Icon>
            获取Table列表
        </Button> &nbsp;

        <Row>
            <template v-for="item in tableList">
                <table-item :dataItem="item"></table-item>
            </template>
        </Row>
    </div>
</template>

<script>
    import constants from '@/constants/constants';
    import requestUtils from '@/request/requestUtils.js';
    import tableItem from './tableItem';
    import * as configUtil from '@/libs/configUtil.js';

    var _ = require('underscore')

    export default {
        data() {
            return {
                tableList: [],
                templateCheckboxList: [],
                chooserAll: true
            };
        },
        components: {
            tableItem
        },
        methods: {
            chooserAllChange(choose) {
                if (choose) {
                    var allList = [];
                    for (var i = 0; i < this.tableList.length; i++) {
                        allList.push(this.tableList[i].desc);
                    }
                    this.templateCheckboxList = allList;
                } else {
                    this.templateCheckboxList = [];
                }
            },
            fetchTableList() {
                var projectKey = this.$store.state.autoCode.defaultProjectKey;
                if (!projectKey) {
                    this.$Message.error({
                        content: '默认项目尚未配置',
                        duration: 5
                    });
                    return;
                }
                let param = {
                    projectKey
                };
                requestUtils.postSubmit(this, constants.urls.autoCode.tableModel.getTableList, param, function (data) {
                    this.tableList = data['value'];
                }, null, true);
            },
            getCheckedTableList() {
                return _.filter(this.tableList, function (item) {
                    return item.checked;
                });
            }
        }
    }
</script>

<style scoped>

</style>