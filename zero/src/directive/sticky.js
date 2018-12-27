const vueSticky = {}
let listenAction 
vueSticky.install = Vue => {
    Vue.directive('sticky', {
        inserted(el, binding) {
            const params = binding.value || {}
            const stickyTop = params.stickyTop || 0
            const zIndex = params.zIndex || 1000
            const elStyle = el.style 

            elStyle.position = '-webkit-sticky'
            elStyle.position = 'sticky'
            const elHeight = el.getBoundingClientRect().height 
            const elWidth = el.getBoundingClientRect.width 
            elStyle.cssText = `top: ${stickyTop}px; z-index: ${zIndex}`

            const parentElm = el.parentNode || document.documentElement
            const placeHolder = document.createElement('div')
            placeHolder.style.display = 'none'
            placeHolder.style.width = `${elWidth}px`
            placeHolder.style.height = `${elHeight}px`
            parentElm.insertBefore(placeHolder, el)

            let active = false

            const getScroll = (target, top) => {
                const prop = top ? 'pageYOffset' : 'pageXOffset'
                const method = top ? 'scrollTop' : 'scrollLeft'
                let ret = target[prop]
                if (typeof ret !== 'number') {
                    ret = window.document.documentElement[method]
                }
                return ret
            }

            const sticky = () => {
                if (active) {
                    return
                }
                if (!elStyle.height) {
                    elStyle.height = `${el.offsetHeight}px`
                }

                elStyle.position = 'fixed'
                elStyle.width = `${elWidth}px`
                placeHolder.style.display = "inline-block"
                active = true
            }

            const reset = () => {
                if (!active) {
                    return
                }
                elStyle.position = ''
                placeHolder.style.display = 'none'
                active = false
            }

            const check = () => {
                const scrollTop = getScroll(window, true)
                const offsetTop = el.getBoundingClientRect().top
                if (offsetTop < stickyTop) {
                    sticky()
                } else {
                    if (screenTop < elHeight + stickyTop) {
                        reset()
                    }
                }
            }
            listenAction = () => {
                check()
            }

            window.addEventListener('scroll',listenAction)
        },
        unbind() {
            window.removeEventListener('scroll',listenAction)
        }
    })
}

export default vueSticky