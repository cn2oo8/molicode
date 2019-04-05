<template>
    <div>
        <Button type="primary" size="small" @click="edit">
            <Icon type="edit"></Icon>
            修改
        </Button>
        <Button type="error" size="small" @click="doDelete">
            <Icon type="trash-a"></Icon>
            删除
        </Button>
    </div>
</template>

<script>
    import requestUtils from '@/request/requestUtils.js'
    import constants from '@/constants/constants.js'

    export default {
        props: {
            item: {
                type: Object,
                required: true
            }
        },
        methods: {
            edit() {
                this.$emit(constants.actions.common.updateItem, this.item)
            },
            doDelete() {
                var params = {'primaryKey': this.item.id}
                this.$Modal.confirm({
                    title: '删除确认',
                    content: '您确定要执行删除操作吗？',
                    onOk: () => {
                        requestUtils.postSubmit(this, constants.urls.conf.acProject.delete, params, function (data) {
                            this.$Message.success({
                                content: '删除成功',
                                duration: 3
                            })
                            this.$emit(constants.actions.common.refreshList)
                        });
                    }
                });
            }
        }
    }
</script>