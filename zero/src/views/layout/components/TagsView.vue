<template>
    <div class="tags-view-container">
        
    </div>
</template>
<script>
import ScrollPane from '@/componets/ScrollPane'
import { generateTitle } from '@/utils/i18n'

export default {
    components: { ScrollPane },
    data() {
        return {
            visible: false,
            top: 0,
            left: 0,
            selectedTag: {}
        }
    },
    computed: {
        visitedViews() {
            return this.$store.state.tagsView.visitedViews
        }
    },
    
}
</script>
