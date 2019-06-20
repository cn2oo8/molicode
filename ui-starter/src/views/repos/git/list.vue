<template>
    <div>

        <Button type="info" @click="open()" v-show="showButton">
            <Icon type="ios-play"></Icon>
            选择仓库
        </Button>


        <Modal v-model="showModal"
               title="git仓库信息" width="85%">

            <Table :columns="columns" :data="repoInfo.repos" size="small" :border="true"></Table>

            <div slot="footer">
                <Button type="info" :loading="loading" @click="loadRepo(true)">
                    <Icon type="refresh"></Icon>
                    刷新
                </Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    import constants from '@/constants/constants';
    import operate from './operate';

    export default {
        name: 'gitRepoList',
        props: {
            repoName: {
                type: String,
                required: true
            },
            showButton: {
                type: Boolean,
                required: false,
                default: true
            }
        },
        data() {
            return {
                showModal: false,
                loading: false,
                repoInfo: {
                    repos: []
                },
                columns: [
                    {
                        title: 'gitUrl',
                        key: 'gitUrl',
                    },
                    {
                        title: 'branchName',
                        key: 'branchName'
                    },
                    {
                        title: '名称',
                        key: 'title'
                    },
                    {
                        title: '模板相对路径',
                        key: 'templateRelativePath'
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 130,
                        align: 'center',
                        render: (h, params) => {
                            return h(operate, {
                                props: {
                                    item: params.row
                                },
                                on: {
                                    'itemChoose': (value) => {
                                        this.itemChoose(value);
                                    }
                                }
                            });
                        }
                    }
                ]
            };
        },
        mounted() {
            this.loadRepo(false);
        },
        methods: {
            open() {
                this.showModal = true;
            },
            loadRepo(refresh) {
                var _this = this;
                let actionName = constants.types.LOAD_REPO_BASE_PROJECT;
                if (this.repoName === 'templateRepo') {
                    actionName = constants.types.LOAD_REPO_TEMPLATE;
                }
                let promise = this.$store.dispatch(actionName, {
                    '_vue': this,
                    refresh: refresh
                });
                promise.then((data) => {
                    _this.repoInfo = data;
                });
            },
            itemChoose(value) {
                this.showModal = false;
                this.$emit(constants.actions.common.itemChoose, value);
            }
        },
        components: {
            operate
        }
    };
</script>

<style scoped>

</style>
