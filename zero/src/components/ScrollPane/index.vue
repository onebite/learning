
<script>
const padding = 15

export default {
    name: 'ScrollPane',
    data() {
        return {
            left: 0
        }
    },
    methods: {
        handleScroll(e) {
            const eventDelta = e.wheelDelta || -e.deltaY * 40
        }
    }    
}
</script>
