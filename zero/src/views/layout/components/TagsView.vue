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
    watch: {
        $route() {
            
        }
    },
    methods: {
        generateTitle,
        isActive(route) {
            return route.path = this.$route.path
        },
        addViewTags() {
            const { name } = this.$route.path
            if (name) {
                this.$store.dispatch('addView',this.$route)
            }
            return false
        },
        moveToCurrentTag() {
            const tags = this.$refs.tag
            this.$nextTick(() => {
                for (const tag of tags) {
                    if (tag.to.path === this.$route.path) {
                        this.$refs.ScrollPane.moveToTarget(tag.$el)
                        if (tag.to.fullPath !== this.$route.fullPath) {
                            this.$store.dispatch('updateVisitedView',this.$route)
                        }
                        break
                    }
                }
            })
        },
        refreshSelectedTag(view) {
            this.$store.dispatch()
        }
    }
}
</script>
