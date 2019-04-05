<template>
    <Col span="4">
        <div style="padding-right: 10px;">
            <Card>
                <p slot="title">#{{dataItem.id}}&nbsp; {{dataItem.name}} </p>
                <p style="height: 20px; margin-bottom: 10px;">
                    <Radio v-model="defaultCheck" @on-change="setDefault">
                        <Tag v-show="isDefaultProject" color="green">默认项目</Tag>
                        <Tag v-show="!isDefaultProject">非默认项目</Tag>
                    </Radio>
                </p>
                <p style="height: 80px;">{{dataItem.remark}}</p>
                <p>
                    <Button type="default" @click="popAction('copyItem')" size="small">复制项目和配置</Button>
                    <operate :item="dataItem" v-on:refreshList="popAction('refreshList')"
                             v-on:updateItem="popAction('updateItem')"
                             :type="2"></operate>
                </p>
            </Card>
        </div>
    </Col>
</template>

<script>
    import operate from './operate.vue';
    import constants from '@/constants/constants.js';

    export default {
        props: {
            dataItem: {
                type: Object
            }
        },
        data() {
            return {
                defaultCheck: this.$store.state.autoCode.defaultProjectKey === this.dataItem.projectKey
            };
        },
        watch: {
            isDefaultProject: function (newVal) {
                this.defaultCheck = newVal;
            }
        },
        computed: {
            defaultProjectKey: function () {
                return this.$store.state.autoCode.defaultProjectKey;
            },
            isDefaultProject: function () {
                return (this.defaultProjectKey === this.dataItem.projectKey);
            }
        },
        methods: {
            popAction(action) {
                this.$emit(action);
            },
            setDefault() {
                var _this = this;
                if (this.$store) {
                    let promise = this.$store.dispatch(constants.types.SAVE_DEFAULT_PROJECT_EXT, {projectKey: this.dataItem.projectKey, project: this.dataItem});
                    promise.then((data) => {
                        _this.$Message.success({
                            content: '设置默认项目成功',
                            duration: 5
                        });
                    });
                }
            }
        },
        components: {
            operate
        }
    };
</script>