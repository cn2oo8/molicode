<template>
    <div>

        <add v-on:refreshList="loadData()"></add>
        <edit ref="editModal" v-on:refreshList="loadData()"></edit>
        <copy ref="copyModal" v-on:refreshList="loadData()"></copy>


        <Row>
            <template v-for="(item, index) in queryResult.dataList">
                <card-item :dataItem="item" v-on:refreshList="loadData()"
                           v-on:updateItem="editItem(item)" v-on:copyItem="copyItem(item)"></card-item>
            </template>
        </Row>

        <div style="margin: 10px;overflow: hidden">
            <div style="float: right;">
                <Page :total="queryResult.pageQuery.totalCount" :pageSize="queryResult.pageQuery.pageSize"
                      :current="queryResult.pageQuery.currentPageNo" @on-page-size-change="loadPageSizeChange"
                      @on-change="loadPageNoChange" show-total show-elevator></Page>
            </div>
        </div>
    </div>
</template>
<script>
    import add from './add.vue';
    import edit from './edit.vue';
    import cardItem from './cardItem'
    import dictSelect from '@/views/common/dict/DictSelect'
    import dictCheckbox from '@/views/common/dict/DictCheckBox'
    import dictRadio from '@/views/common/dict/DictRadio'
    import requestUtils from '@/request/requestUtils.js'
    import constants from '@/constants/constants';
    import copy from './copy';

    export default {
        data() {
            return {
                loading: false,
                queryResult: {
                    dataList: [],
                    pageQuery: {
                        totalCount: 0,
                        pageCount: 0,
                        currentPageNo: 1,
                        pageSize: 4
                    }
                },
                formSearch: {
                    'id': '',
                    'name': '',
                    'type': 1,
                    'status': 1

                },
                constants
            };
        },
        beforeMount() {
            this.loadDefaultProject();
        },
        mounted() {
            this.loadData();
        },
        methods: {
            loadPageSizeChange(pageSize) {
                this.queryResult.pageQuery.pageSize = pageSize
                this.loadData()
            },
            loadPageNoChange(pageNo) {
                this.queryResult.pageQuery.pageNo = pageNo
                this.loadData()
            },
            loadData() {
                var _this = this
                var searchParam = requestUtils.serializeObject(this.formSearch, true, true)
                searchParam['pageSize'] = _this.queryResult.pageQuery.pageSize
                searchParam['page'] = _this.queryResult.pageQuery.pageNo
                requestUtils.postSubmit(_this, constants.urls.conf.acProject.list, searchParam, function (data) {
                    _this.queryResult.dataList = data.value
                    _this.queryResult.pageQuery = data.pageQuery
                }, null, true);
            },
            editItem: function (item) {
                this.$refs.editModal.editItem(item);
            },
            copyItem: function(item){
                this.$refs.copyModal.editItem(item);
            },
            loadDefaultProject() {
                if (this.$store) {
                    let promise = this.$store.dispatch(constants.types.LOAD_DEFAULT_PROJECT, {});
                    promise.then((data) => {});
                }
            }
        },
        components: {
            add,
            edit,
            dictSelect,
            dictCheckbox,
            dictRadio,
            cardItem,
            copy
        }
    }

</script>