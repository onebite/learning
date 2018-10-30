<template>
    <el-dropdown trigger="click" @command="handleSetSize">
        <el-dropdown-item :disable="size==='medium'" command="medium">Medium></el-dropdown-item>
        <el-dropdown-item :disable="size==='small'" command="small">Smaill></el-dropdown-item>
        <el-dropdown-item :disable="size==='mini'" command="mini">Mini></el-dropdown-item>
    </el-dropdown>
</template>

<script>
export default {
    computed: {
        size() {
            return this.$store.getters.size
        }
    },
    methods: {
        handleSetSize(size) {
            this.$ELEMENT.size = size
            this.$store.dispatch('setSize',size)
            this.refreshView()
            this.$message({
                message: 'Switch size success',
                type: 'success'
            })
        },
        refreshView() {
            //In order to make the cached page re-rendered
            this.$store.dispatch('delAllCachedViews',this.$route)

            const { fullPath } = this.$route
            this.$nextTick(() => {
                path: '/redirect' + fullPath
            })
        }
    }
}
</script>
<style scoped>
.size-icon {
    font-size: 20px;
    cursor: pointer;
    vertical-align: -4px!important;
}
</style>
