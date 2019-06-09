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
    import constants from '@/constants/constants';
    import * as renderUtil from '@/libs/renderUtil.js';

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
                        title: '文件(点击下载)',
                        key: 'zipUrl',
                        render: (h, params) => {
                            return h('a', {
                                attrs: {
                                    href: params.row.zipUrl,
                                    target: '_blank'
                                }
                            }, [params.row.zipUrl]);
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
                /*  let newList = [result];
                  _.each(this.resultList, function (item) {
                      newList.push(item);
                  });
                  */
                this.resultList.push(result);
            }
        }
    }
</script>

<style scoped>

</style>
