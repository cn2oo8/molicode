<template>
    <div>
        <Button type="primary" size="small" @click="edit">
            <Icon type="edit"></Icon>
            修改
        </Button>

    </div>
</template>

<script>
    import constants from '@/constants/constants.js';

    export default {
        props: {
            'item': {
                type: Object,
                required: true
            }
        },
        methods: {
            edit () {
                this.$emit(constants.actions.common.updateItem, this.item);
            }
        }
    };
</script>
