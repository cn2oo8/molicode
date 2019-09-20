<template>
    <div>
        <Button @click="show" :type="editType" size="small">
            {{editText}}
        </Button>
        <Modal v-model="showModal"
               :title="title"
               @on-cancel="cancel"
               :mask-closable="false"
               width="80%">


            <Form :label-width="120" inline>
                <Row>
                    <Col span="24">
                        <Form-item label="扩展json" prop="customPropsText" style="width: 90%">
                            <Input v-model="customPropsText" type="textarea"
                                   :autosize="{minRows:10,maxRows: 10}"></Input>
                        </Form-item>
                    </Col>
                </Row>

            </Form>


            <div slot="footer">
                <Button type="default" @click="cancel">
                    取消
                </Button>
                <Button type="primary" @click="save">
                    <Icon type="save"></Icon>
                    保存
                </Button>
            </div>
        </Modal>
    </div>
</template>

<script>
    var _ = require('underscore')

    export default {
        name: 'customProps',
        props: {
            customPropsObject: {
                type: Object,
                required: false
            }
        },
        data() {
            let customPropsObject = this.customPropsObject;
            let editType = 'primary';
            if (customPropsObject === null || customPropsObject === undefined) {
                customPropsObject = {};
            }
            let editText = '修改'
            if (_.isEmpty(customPropsObject)) {
                editText = '新增';
                editType = 'info';
            }
            let customPropsText = JSON.stringify(customPropsObject, null, 2);
            return {
                title: '修改customProps属性',
                showModal: false,
                customPropsText,
                editText,
                customPropsObjectParam: customPropsObject,
                editType: 'info'
            };
        },
        methods: {
            show() {
                this.showModal = true;
            },
            cancel() {
                this.showModal = false;
            },
            save() {
                let customPropsObject = null;
                try {
                    customPropsObject = JSON.parse(this.customPropsText);
                } catch (e) {
                    this.$Message.error({
                        content: '配置json数据有问题，请检查！',
                        duration: 5
                    });
                    return;
                }
                this.propsChange(customPropsObject);
                this.customPropsObjectParam = customPropsObject;
                this.$emit('on-change', customPropsObject);
                this.showModal = false;
            },
            propsChange(props) {
                if (_.isEmpty(props)) {
                    this.editText = '新增';
                    this.editType = 'info';
                } else {
                    this.editText = '修改';
                    this.editType = 'primary';
                    this.customPropsText = JSON.stringify(props, null, 2);
                }
            }
        }
    };
</script>

<style scoped>

</style>
