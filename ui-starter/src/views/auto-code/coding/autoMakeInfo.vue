<template>
    <div>
        <Button size="small" type="default" @click="show()">？关于模板工程</Button>
        <Modal v-model="showModal"
               title="模板工程信息" width="780"
               @on-ok="close">

            <Form :label-width="120" inline v-if="autoMake !=null ">
                <Row>
                    <Col span="24">
                        <Form-item label="引擎版本:" prop="moliCodeVersion" style="width: 90%">
                            {{autoMake.moliCodeVersion}}
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="groupId:" prop="groupId" style="width: 90%">
                            {{mavenResource.groupId}}
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="artifactId:" prop="artifactId" style="width: 90%">
                            {{mavenResource.artifactId}}
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="version:" prop="version" style="width: 90%">
                            {{mavenResource.version}}
                        </Form-item>
                    </Col>
                </Row>

                <Row v-show="mavenResource.name != null">
                    <Col span="24">
                        <Form-item label="name:" prop="name" style="width: 90%">
                            {{mavenResource.name}}
                        </Form-item>
                    </Col>
                </Row>

                <Row v-show="mavenResource.description != null">
                    <Col span="24">
                        <Form-item label="description:" prop="description" style="width: 90%">
                            <p style="word-break:break-all">
                                {{mavenResource.description}}
                            </p>
                        </Form-item>
                    </Col>
                </Row>

                <Row v-show="mavenResource.developersJson != null">
                    <Col span="24">
                        <Form-item label="developers:" prop="developersJson" style="width: 90%">
                            <p style="word-break:break-all">
                                {{mavenResource.developersJson}}
                            </p>
                        </Form-item>
                    </Col>
                </Row>

                <Row v-show="mavenResource.url != null">
                    <Col span="24">
                        <Form-item label="url:" prop="url" style="width: 90%">
                            {{mavenResource.url}}
                        </Form-item>
                    </Col>
                </Row>


                <Row v-show="mavenResource.inceptionYear != null">
                    <Col span="24">
                        <Form-item label="inceptionYear:" prop="inceptionYear" style="width: 90%">
                            {{mavenResource.inceptionYear}}
                        </Form-item>
                    </Col>
                </Row>
            </Form>


            <Alert type="warning" v-if="autoMake ==null ">请先拉取模板列表再查看！</Alert>
        </Modal>
    </div>
</template>

<script>
    export default {
        name: 'autoMakeInfo',
        data() {
            return {
                showModal: false,
                autoMake: null,
                mavenResource: {}
            };
        },
        computed: {
            defAutoMake: function () {
                return this.$store.state.autoCode.autoMake;
            }
        },
        watch: {
            defAutoMake: function (newVal) {
                this.autoMake = newVal;
                this.mavenResource = newVal.mavenResourceVo;
            }
        },
        methods: {
            close() {
                this.showModal = false;
            },
            show() {
                this.showModal = true;
            }
        }
    }
</script>

<style scoped>

</style>