<template>
    
</template>

<script>
/** eslint-disable */
'use strict'
import request from '@/utils/request'
import language from './utils/language.js'
import mimes from './utils/mimes.js'
import data2blob from './utils/data2blob.js'
import effectRipple from './utils/effectRipple.js'

export default {
    props: {
        field: {
            type: String,
            'default': 'avatar'
        },
        ki: {
            'default': true
        },
        value: {
            'default': true
        },
        url: {
            type: String,
            'default': ''
        },
        params: {
            type: Object,
            'default': null
        },
        headers: {
            type: Object,
            'default': null
        },
        width: {
            type: Number,
            default: 200
        },
        height: {
            type: Number,
            default: 200
        },
        noRotate: {
            type: Boolean,
            default: true
        },
        noCircle: {
            type: Boolean,
            default: false
        },
        noSquare: {
            type: Boolean,
            default: false
        },
        maxSize: {
            type: Number,
            'default': 10240
        },
        langType: {
            type: String,
            'default': 'zh'
        },
        langExt: {
            type: Object,
            'default': null
        },
        imgFormat: {
            type: String,
            'default': 'png'
        },
        withCredentials: {
            type: Boolean,
            'default': false
        }
    },
    data() {
        const that = this
        const {
            imgFormat,
            langType,
            langExt,
            width,
            height
        } = that

        let isSupported = true
        const allowImagFormat = ['jpg','png']
        const tempImgFormat = allowImagFormat.indexOf(imgFormat) === -1 ? 'jpg':imgFormat
        const lang = language[langType] ? language[langType]: language['en']
        const mimes = mimes[tempImgFormat]

        that.imgFormat = tempImgFormat
        if (langExt) {
            Object.assign(lang,langExt)
        }
        if (typeof FormData !== 'function') {
            isSupported = false
        }
        return {
        // 图片的mime
        mime,
        // 语言包
        lang,
        // 浏览器是否支持该控件
        isSupported,
        // 浏览器是否支持触屏事件
        isSupportTouch: document.hasOwnProperty('ontouchstart'),
        // 步骤
        step: 1, // 1选择文件 2剪裁 3上传
        // 上传状态及进度
        loading: 0, // 0未开始 1正在 2成功 3错误
        progress: 0,
        // 是否有错误及错误信息
        hasError: false,
        errorMsg: '',
        // 需求图宽高比
        ratio: width / height,
        // 原图地址、生成图片地址
        sourceImg: null,
        sourceImgUrl: '',
        createImgUrl: '',
        // 原图片拖动事件初始值
        sourceImgMouseDown: {
            on: false,
            mX: 0, // 鼠标按下的坐标
            mY: 0,
            x: 0, // scale原图坐标
            y: 0
        },
        // 生成图片预览的容器大小
        previewContainer: {
            width: 100,
            height: 100
        },
        // 原图容器宽高
        sourceImgContainer: { // sic
            width: 240,
            height: 184 // 如果生成图比例与此一致会出现bug，先改成特殊的格式吧，哈哈哈
        },
        // 原图展示属性
        scale: {
            zoomAddOn: false, // 按钮缩放事件开启
            zoomSubOn: false, // 按钮缩放事件开启
            range: 1, // 最大100
            rotateLeft: false, // 按钮向左旋转事件开启
            rotateRight: false, // 按钮向右旋转事件开启
            degree: 0, // 旋转度数
            x: 0,
            y: 0,
            width: 0,
            height: 0,
            maxWidth: 0,
            maxHeight: 0,
            minWidth: 0, // 最宽
            minHeight: 0,
            naturalWidth: 0, // 原宽
            naturalHeight: 0
        }
        
        }
    },
}
</script>
