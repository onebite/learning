<template>
    <div class="components-container">
        <code>This is base on 
            <a class="link-type" href="//github.com/dai-siki/vue-image-crop-upload"> vue-image-crop-upload</a>
            {{ $t('components.imageUploadTips') }}
        </code>
        <pan-thumb :image="image"/>

        <el-button type="primary" icon="upload" style="position: absolute;bottom: 15px;margin-left: 40px;" @click="imagecropperShow=true">Change Avatar</el-button>

        <image-cropper 
            v-show="imagecropperShow" 
            :width="300" 
            :height="300" 
            :key="imagecropperKey" 
            url="https://httpbin.org/post" 
            lang-type="en"
            @close="close"
            @crop-upload-success="cropSuccess" />
    </div>
</template>

<script>

</script>
