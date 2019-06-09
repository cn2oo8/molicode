<template>
    <div>
        <Input v-model="model" @on-change="updateValue" :disabled="disabled">
        <Button slot="append" icon="ios-search" type="primary" @click="fileChooser" :disabled="disabled" v-show="windowName!='headless'">选择</Button>
        <Button slot="append" icon="document" @click="openFile" v-show="windowName!='headless'">open</Button>
        </Input>

    </div>
</template>

<script>
    import constants from '@/constants/constants';
    import requestUtils from '@/request/requestUtils.js';

    export default {
        name: 'fileChooser',
        props: {
            value: {
                type: String,
                default: ''
            },
            dialogType: {
                type: String,
                default: 'open'
            },
            fileType: {
                type: String,
                default: 'all'
            },
            fileExt: {
                type: String,
                default: ''
            },
            disabled: {
                type: Boolean,
                default: false
            },
            changeCurPath: {
                type: String,
                default: ''
            },
            parentPath: {
                type: String,
                default: ''
            }
        },
        data() {
            return {
                model: this.value,
                windowName: this.$store.state.autoCode.profile['browserWindowName']
            };
        },
        watch: {
            value(newVal, oldVal) {
                this.model = newVal;
            }
        },
        methods: {
            updateValue() {
                let v = this.model
                this.$emit('input', v);
            },
            fileChooser: function () {
                requestUtils.postSubmit(this, constants.urls.common.file.fileChooser, this.getRequestData(), function (data) {
                    if (data.value !== null && data.value !== '') {
                        this.model = data.value;
                        this.updateValue();
                    }
                }, null, false);

                var windowName = this.$store.state.autoCode.profile['browserWindowName'];
                if (windowName === 'swing') {
                    this.$Message.warning({
                        content: '文件在swing窗口中打开，请切换界面进行文件选择！',
                        duration: 5
                    });
                }
            },
            openFile: function () {
                let requestData = this.getRequestData();
                requestData['editFilePath'] = this.model;
                if (requestData['editFilePath'] === '') {
                    this.$Message.error({
                        content: 'file path can not be empty！',
                        duration: 5
                    })
                    return;
                }

                requestUtils.postSubmit(this, constants.urls.common.file.openFile, requestData, function (data) {
                    this.$Message.info({
                        content: 'file open success!',
                        duration: 5
                    });
                }, null, false);
            },
            getRequestData () {
                var parentPath = this.parentPath;
                if (!parentPath) {
                    parentPath = this.model;
                }
                return {
                    dialogType: this.dialogType,
                    fileType: this.fileType,
                    fileExt: this.fileExt,
                    parentPath: parentPath,
                    changeCurPath: this.changeCurPath
                };
            }
        }
    }
    ;
</script>

<style scoped>

</style>
