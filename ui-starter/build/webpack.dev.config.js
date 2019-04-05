const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const merge = require('webpack-merge');
const webpackBaseConfig = require('./webpack.base.config.js');
const fs = require('fs');
const package = require('../package.json');

fs.open('./build/env.js', 'w', function (err, fd) {
    const buf = 'export default "development";';
    fs.write(fd, buf, 0, buf.length, 0, function (err, written, buffer) {
    });
});

module.exports = merge(webpackBaseConfig, {
    devtool: '#source-map',
    output: {
        publicPath: '/dist/',
        filename: '[name].js',
        chunkFilename: '[name].chunk.js'
    },
    plugins: [
        new ExtractTextPlugin({
            filename: '[name].css',
            allChunks: true
        }),
        new webpack.optimize.CommonsChunkPlugin({
            name: ['vender-exten', 'vender-base'],
            minChunks: Infinity
        }),
        new HtmlWebpackPlugin({
            title: 'MoliCode自动代码生成' + package.version,
            filename: '../index.html',
            inject: false
        }),
        new CopyWebpackPlugin([
            {
                from: 'src/views/main-components/theme-switch/theme'
            }
        ], {
            ignore: [
                'text-editor.vue'
            ]
        })
    ],
    devServer: {
        historyApiFallback: true,
        quiet: false, //控制台中不输出打包的信息
        noInfo: false,
        hot: true, //开启热点
        inline: true, //开启页面自动刷新
        lazy: false, //不启动懒加载
        progress: true, //显示打包的进度
        watchOptions: {
            aggregateTimeout: 300
        },
        port: '8097', //设置端口号
        //其实很简单的，只要配置这个参数就可以了
        proxy: {
            '/conf/': {
               target: 'http://localhost.shareyi.com:8098/',
                host:'http://localhost.shareyi.com',
                secure: false,
                changeOrigin: true
            },
            '/common/': {
                target: 'http://localhost.shareyi.com:8098/',
                host:'http://localhost.shareyi.com',
                secure: false,
                changeOrigin: true
            },
            '/sys/': {
                target: 'http://localhost.shareyi.com:8098/',
                host:'http://localhost.shareyi.com',
                secure: false,
                changeOrigin: true
            },
            '/autoCode/': {
                target: 'http://localhost.shareyi.com:8098/',
                host:'http://localhost.shareyi.com',
                secure: false,
                changeOrigin: true
            }

        }

    }
});
