<template>
    <div>

        <Card>
            <p slot="title">
                <Icon type="soup-can"></Icon>
                浏览器辅助功能
            </p>

            <p>
                <Form :model="formItem" :label-width="80">
                    <FormItem label="jump URL">
                        <Input v-model="formItem.url" placeholder="please input http url" @keyup.enter.native="jump">
                        <Button slot="append" icon="ios-search" @click="jump">JUMP</Button>
                        </Input>
                    </FormItem>
        </Form>
            </p>
        </Card>
    </div>
</template>

<script>
    export default {
        name: 'browser',
        data() {
            return {
                formItem: {
                    url: 'http://www.jd.com'
                }
            };
        },
        methods: {
            jump() {
                let url = this.formItem.url;
                if (url === null || url.length < 10) {
                    this.$Message.error({
                        content: 'please input legal url...',
                        duration: 5
                    })
                    return;
                }
                window.location.href = this.formItem.url;
            }
        }
    };
</script>

<style scoped>

</style>
