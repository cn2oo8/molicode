<template>
    <Col span="8">
        <div style="padding-right: 10px;padding-bottom: 3px;">
            <Card>
                <p>
                    <checkbox :label="dataItem.tableName" v-model="dataItem.checked">
                        {{dataItem.tableName}}
                    </checkbox>
                </p>
                <p>
                    <Tag>中文名称</Tag>
                    <Input v-model="dataItem.cnname" placeholder="请输入中文名称"></Input>
                </p>
            </Card>
        </div>
    </Col>
</template>

<script>
    import constants from '@/constants/constants.js';
    import requestUtils from '@/request/requestUtils.js'

    export default {
        props: {
            dataItem: {
                type: Object
            }
        },
        data() {
            this.dataItem.checked = false;
            return {};
        },
        methods: {}
    };
</script>
