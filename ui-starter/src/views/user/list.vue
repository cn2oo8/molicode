<template>
    <div>

        <Form ref="formSearch" :model="formSearch" :rules="searchFormRules" :label-width="66">
            <Row>

                <Col span="6">
                    <FormItem label="用户名称" prop="userName">
                        <Input v-model="formSearch.userName" placeholder="请输入用户名称"></Input>
                    </FormItem>
                </Col>

                <Col span="6">
                    <FormItem label="用户昵称" prop="nickName">
                        <Input v-model="formSearch.nickName" placeholder="请输入用户昵称"></Input>
                    </FormItem>
                </Col>

                <Col span="6">
                    <FormItem label="角色码" prop="roleCode">
                        <Input v-model="formSearch.roleCode" placeholder="请输入角色码"></Input>
                    </FormItem>
                </Col>

                <Col span="6">
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
                        <Button type="primary" @click="loadPageNoChange(1)" :loading="loading">
                            <Icon type="search"></Icon>
                            查询
                        </Button>
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
                      :current="queryResult.pageQuery.currentPageNo" @on-page-size-change="loadPageSizeChange"
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
    import requestUtils from '@/request/requestUtils.js'
    import constants from '@/constants/constants';

    export default {
        name: 'userList',
        mixins: [tableDefine],
        methods: {
            loadPageSizeChange(pageSize) {
                this.queryResult.pageQuery.pageSize = pageSize
                this.loadData()
            },
            loadPageNoChange(pageNo) {
                this.queryResult.pageQuery.currentPageNo = pageNo
                this.loadData()
            },
            loadData() {
                var _this = this

                this.$refs['formSearch'].validate((valid) => {
                    if (valid) {
                        var searchParam = requestUtils.serializeObject(this.formSearch, true, true)
                        searchParam['pageSize'] = _this.queryResult.pageQuery.pageSize;
                        searchParam['page'] = _this.queryResult.pageQuery.currentPageNo;
                        requestUtils.postSubmit(_this, constants.urls.sys.acUser.list, searchParam, function (data) {
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
