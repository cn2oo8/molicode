<template>
    <div>
        <Button @click="clear" type="info" size="small">
            <Icon type="ios-close-outline"></Icon>
            清空记录
        </Button> &nbsp;

        <Row>
            <Table :columns="columns" :data="resultList" size="small" :border="true"></Table>
        </Row>
    </div>
</template>

<script>
    import * as renderUtil from '@/libs/renderUtil.js';
    import codePreview from '../file/codePreview'

    var _ = require('underscore')

    export default {
        data() {
            return {
                resultList: [],
                columns: [
                    {
                        title: '时间',
                        key: 'startTime',
                        width: 160,
                        render: (h, params) => {
                            return h('div', renderUtil.formatDateTime(params.row.startTime));
                        }
                    },
                    {
                        title: '耗时(ms)',
                        key: 'costTime',
                        width: 160
                    },
                    {
                        title: '文件(复制到浏览器 or 点击下载)',
                        key: 'zipUrl',
                        render: (h, params) => {
                            return h('a', {
                                attrs: {
                                    href: params.row.zipUrl,
                                    target: '_blank'
                                }
                            }, [params.row.zipUrl]);
                        }
                    },
                    {
                        title: '操作',
                        width: 150,
                        render: (h, params) => {
                            return h(codePreview, {
                                props: {
                                    showButton: true,
                                    item: params.row
                                }
                            });
                        }
                    }
                ]
            };
        },
        methods: {
            clear() {
                this.resultList = [];
            },
            appendResultInfo(result) {
                this.resultList.push(result);
            }
        },
        components: {
            codePreview
        }
    }
</script>

<style scoped>

</style>
