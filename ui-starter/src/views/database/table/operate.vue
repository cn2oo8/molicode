<template>
    <div>
        <edit ref="editModal" :item="item" v-on:genCode="genCode" v-on:tableModelNotify="tableModelNotify"></edit>
        <Button type="primary" size="small" @click="edit">
            <Icon type="edit"></Icon>
            配置生成代码
        </Button>
    </div>
</template>

<script>
    import constants from '@/constants/constants.js';
    import edit from './edit';

    export default {
        props: {
            item: {
                type: Object,
                required: true
            }
        },
        methods: {
            edit() {
                this.$refs.editModal.show(true);
            },
            genCode(value) {
                this.$emit(constants.actions.autoCode.genCode, value);
            },
            tableModelNotify(tableModel){
                this.$emit(constants.actions.autoCode.tableModelNotify, tableModel);
            }
        },
        components: {
            edit
        }
    }
</script>
