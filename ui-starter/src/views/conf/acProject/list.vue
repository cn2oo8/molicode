<template>
    <div>

        <Form ref="formSearch" :model="formSearch" :rules="searchFormRules" :label-width="80">
            <Row>

                <Col span="7">
                    <FormItem label="id" prop="id">
                        <Input v-model="formSearch.id" placeholder="请输入id"></Input>
                    </FormItem>
                </Col>

                <Col span="7">
                    <FormItem label="名称" prop="name">
                        <Input v-model="formSearch.name" placeholder="请输入名称"></Input>
                    </FormItem>
                </Col>

                <Col span="7">
                    <FormItem label="类型" prop="type">
                        <dict-select v-model="formSearch.type"
                                     :kind="this.constants.dicts.dictKinds.PROJECT_TYPE_DICT"
                                     :clearable="true"></dict-select>
                    </FormItem>
                </Col>
            </Row>
            <Row>

                <Col span="7">
                    <FormItem label="状态" prop="status">
                        <dict-select v-model="formSearch.status"
                                     :kind="this.constants.dicts.dictKinds.STD_STATUS"
                                     :clearable="true"></dict-select>
                    </FormItem>
                </Col>
            </Row>

            <Row>
                <Col span="4" offset="20">
                    <Form-item>
                        <Button type="primary" @click="loadPageNoChange(1)" :loading="loading"><Icon type="search"></Icon> 查询</Button>
                    </Form-item>
                </Col>
            </Row>

        </Form>


        <add v-on:refreshList="loadData()"></add>
        <edit ref="editModal" v-on:refreshList="loadData()"></edit>

        <Table border :columns="columns" :data="queryResult.dataList" :loading="loading"></Table>

        <div style="margin: 10px;overflow: hidden">
            <div style="float: right;">
                <Page :total="queryResult.pageQuery.totalCount" :pageSize="queryResult.pageQuery.pageSize"
                      :current="queryResult.pageQuery.pageNo" @on-page-size-change="loadPageSizeChange"
                      @on-change="loadPageNoChange" show-total show-elevator show-sizer></Page>
            </div>
        </div>
    </div>
</template>
<script>
    import add from './add.vue';
    import edit from './edit.vue';
    import dictSelect from '@/views/common/dict/DictSelect'
    import dictCheckbox from '@/views/common/dict/DictCheckBox'
    import dictRadio from '@/views/common/dict/DictRadio'

    import tableDefine from './tableDefine.js'
    import * as renderUtil from '@/libs/renderUtil.js'
    import requestUtils from '@/request/requestUtils.js'
    import constants from '@/constants/constants';

    export default {
        mixins: [tableDefine],
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
                this.$refs['formSearch'].validate((valid) => {
                    if (valid) {
                        var searchParam = requestUtils.serializeObject(this.formSearch, true, true)
                        searchParam['pageSize'] = _this.queryResult.pageQuery.pageSize
                        requestUtils.postSubmit(_this, constants.urls.conf.acProject.list, searchParam, function (data) {
                            _this.queryResult.dataList = data.value
                            _this.queryResult.pageQuery = data.pageQuery
                        }, null, true)
                    } else {
                        this.$Message.error('Fail!');
                    }
                })
            },
            editItem: function (item) {
                this.$refs.editModal.editItem(item)
            }
        },
        components: {
            add,
            edit,
            dictSelect,
            dictCheckbox,
            dictRadio
        }
    }

</script>