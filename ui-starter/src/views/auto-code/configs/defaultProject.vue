<template>
    <div>
        <Row style="padding-bottom: 10px;">
            <Col span="3" style="text-align: right; padding-right: 10px;">
                项目ID:
            </Col>
            <Col span="6">
                <Tag color="green">默认项目</Tag>
                {{project.id}}

            </Col>

            <Col span="3" style="text-align: right; padding-right: 10px;">
                项目key:
            </Col>
            <Col span="9">
                {{project.projectKey}}
            </Col>


        </Row>

        <Row style="padding-bottom: 10px;">

            <Col span="3" style="text-align: right; padding-right: 10px;">
                项目名称:
            </Col>
            <Col span="6">
                {{project.name}}
            </Col>

            <Col span="3" style="text-align: right; padding-right: 10px;">
                创建时间:
            </Col>
            <Col span="9">
                {{project.created}}
            </Col>
        </Row>

        <Row style="padding-bottom: 10px;">
            <Col span="3" style="text-align: right; padding-right: 10px;">
                项目详情:
            </Col>
            <Col span="21">
                {{project.remark}}
            </Col>

        </Row>
    </div>
</template>

<script>

    export default {
        name: 'defaultProject',
        data: function () {
            return {
                project: {}
            };
        },
        methods: {
            notifyProject(project) {
                this.project = project;
            }
        }
    };
</script>

<style scoped>

</style>
