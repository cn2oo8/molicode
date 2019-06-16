<template>
    <div>
        <Button type="primary" size="small" @click="choose">
            <Icon type="ok"></Icon>
            选择
        </Button>

        <Button type="info" size="small" @click="description">
            详情
        </Button>

        <Modal v-model="showModal"
               title="git仓库信息" width="600">
            <Form :label-width="120" inline>

                <Row>
                    <Col span="24">
                        <Form-item label="title" prop="title">
                            {{item.title}}
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="gitUrl" prop="gitUrl">
                            {{item.gitUrl}}
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="branchName" prop="branchName">
                            {{item.branchName}}
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="作者" prop="author">
                            {{item.author}}
                        </Form-item>
                    </Col>
                </Row>

                <Row>
                    <Col span="24">
                        <Form-item label="主页" prop="homePage">
                            <a :href="item.homePage" target="_blank">{{item.homePage}}</a>
                        </Form-item>
                    </Col>
                </Row>

                <Row v-show="item.templateRelativePath">
                    <Col span="24">
                        <Form-item label="模板相对路径" prop="templateRelativePath">
                            {{item.templateRelativePath}}
                        </Form-item>
                    </Col>
                </Row>


            </Form>
        </Modal>
    </div>
</template>

<script>
    import constants from '@/constants/constants.js';

    export default {
        props: {
            item: {
                type: Object,
                required: true
            }
        },
        data() {
            return {
                showModal: false
            };
        },
        methods: {
            choose() {
                this.$emit(constants.actions.common.itemChoose, this.item);
            },
            description() {
                this.showModal = true;
            }
        }
    }
</script>
