<template>
    <div>
        <Button @click="clearMessage">
            <Icon type="trash-a"></Icon>
            清空日志信息
        </Button>

        <div style="height: 450px; overflow-y: scroll; background: #333; color: #aaa; padding: 10px;">
            <pre v-for="message in messageList" class="message_item_pre">{{message}}</pre>
        </div>
    </div>
</template>

<script>
    var _ = require('underscore')

    export default {
        name: 'log-console',
        props: {
            sid: {
                type: String,
                default: ''
            }
        },
        data() {
            return {
                messageList: [],
                webSocket: null
            };
        },
        mounted() {
            this.connectServer();
        },
        methods: {
            connectServer: function () {
                var _this = this;
                var webSocket = this.webSocket;
                if (webSocket === null || webSocket.readyState === 2 || webSocket.readyState === 3) {
                    if (this.sid === null || this.sid === '') {
                        _this.appendMessage('sid is null ,can not connect to server');
                        return;
                    }
                    // webSocket = new WebSocket('ws://' + host + '/websocket');
                    var url = window.location.host;
                    webSocket = new WebSocket('ws://' + url + '/websocket/' + this.sid);
                    // 连接成功建立的回调方法
                    webSocket.onopen = function (event) {
                        _this.appendMessage('log connection connected');
                    }

                    // 接收到消息的回调方法
                    webSocket.onmessage = function (event) {
                        _this.appendMessage(event.data);
                    }

                    // 连接关闭的回调方法
                    webSocket.onclose = function () {
                        _this.appendMessage('log connection has been closed');
                    }
                    this.webSocket = webSocket;
                }
            },
            appendMessage(msg) {
                this.messageList.push(msg);
            },
            clearMessage() {
                this.messageList = [];
            }
        },
        beforeDestroy() {
            if (this.webSocket !== null && this.webSocket.readyState === 1) {
                this.webSocket.close();
                this.webSocket = null;
            }
        }
    };
</script>

<style scoped>
    .message_item_pre {
        margin: 0px;
        white-space: pre-wrap;
        word-wrap: break-word;
    }
</style>
