<template>
    <div>
        <Modal v-model="showModal"
               title="模板信息"
               @on-ok="close" width="780">
            <Form :label-width="120" inline>
                <Row>
                    <Col span="24">
                        <Form-item label="模板ID:" prop="id" style="width: 90%">
                           {{template.id}}
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="模板名称:" prop="name" style="width: 90%">
                            {{template.name}}
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="描述信息:" prop="desc" style="width: 90%">
                            {{template.desc}}
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="支持的数据模型:" prop="acceptDataModel" style="width: 90%">
                            {{template.acceptDataModel}}
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="模板目录:" prop="templateFile" style="width: 90%">
                            {{template.templateFile}}
                        </Form-item>
                    </Col>
                </Row>



                <Row>
                    <Col span="24">
                        <Form-item label="输出目录:" prop="destFile" style="width: 90%">
                            {{template.destFile}}
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="模板引擎:" prop="engine" style="width: 90%">
                            {{template.engine}}
                        </Form-item>
                    </Col>
                </Row>
            </Form>
        </Modal>
    </div>
</template>

<script>
    export default {
        name: 'TemplateInfo',
        data() {
            return {
                showModal: false,
                template: null
            };
        },
        methods: {
            close() {
                this.showModal = false;
            },
            show(template) {
                this.showModal = true;
                this.template = template;
            }
        }
    }
</script>

<style scoped>

</style>