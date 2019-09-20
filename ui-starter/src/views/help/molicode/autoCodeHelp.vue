<template>
    <div>
        <h2>帮助中心</h2>

        <br/>
        <h3>请通过浏览器访问以下网址:</h3>
        <Form ref="formItems" :model="formItems" :rules="formRules" :label-width="120" inline>
            <Row>
                <Col span="24">
                    <Form-item label="帮助中心" style="width: 96%">
                        <Input value="https://cn2oo8.github.io/molicode_doc/"></Input>

                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="相关案例说明" style="width: 96%">
                        <Input value="https://my.oschina.net/davidzhang?q=MoliCode"></Input>

                    </Form-item>
                </Col>
            </Row>

            <Row>
                <Col span="24">
                    <Form-item label="最新版本下载&源码" style="width: 96%">
                        <Input value="https://gitee.com/zhangshibin1987/molicode/releases"></Input>

                    </Form-item>
                </Col>
            </Row>

        </Form>

    </div>
</template>

<script>
    export default {
        name: 'tableModelHelp'
    }
</script>

<style scoped>

</style>
