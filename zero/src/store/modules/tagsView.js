import { resolve } from "url";

const tagsView = {
    state: {
        visitedViews: [],
        cachedViews: []
    },
    mutations: {
        ADD_VISITED_VIEW: (state, view) => {
            if (state.visitedViews.some(v => v.path === view.path)) return
            state.visitedViews.push(
                //浅拷贝
                Object.assign({}, view, {
                    titile: view.meta.title || 'no-name'
                })
            )
        },
        ADD_CACHED_VIEW: (state, view) => {
            if (state.cachedViews.includes(view.name)) return
            if (!view.meta.noCache) {
                state.cachedViews.push(view.name)
            }
        },
        DEL_VISITED_VIEW: (state, view) => {
            //遍历删除，slow
            for (const [i, v] of state.visitedViews.entries()) {
                if (v.path === view.path) {
                    state.visitedViews.splice(i, 1)
                    break
                }
            }
        },
        DEL_CACHED_VIEW: (state, view) => {
            for (const i of state.cachedViews) {
                if (i === view.name) {
                    const index = state.cachedViews.indexOf(i)
                    state.cachedViews.splice(index, 1)
                    break
                }
            }
        },
    
        DEL_OTHERS_VISITED_VIEWS: (state, view) => {
            for (const [i, v] of state.visitedViews.entries()) {
                if (v.path === view.path) {
                    state.visitedViews = state.visitedViews.slice(i,i + 1)
                    break
                }
            }
        },
        DEL_OTHERS_CACHED_VIEWS: (state, view) => {
            for (const i of state.cachedViews) {
                if (i === view.name) {
                    const index = state.cachedViews.slice(index, index+1)
                    break;
                }
            }
        },
    
        DEL_ALL_VISITED_VIEWS: state => {
            state.visitedViews = []
        },
        DEL_ALL_CACHED_VIEWS: state => {
            state.cachedViews = []
        },
    
        UPDATE_VISITED_VIEW: (state, view) => {
            for (let v of state.visitedViews) {
                if (v.path === view.path) {
                    v = Object.assign(v, view)
                    break
                }
            }
        }
    },

    actions: {
        addView({ dispatch }, view) {
            dispatch('')
        },
        addVisitedView({ commit }, view) {
            commit('ADD_VISITED_VIEW', view)
        },
        addCachedView({ commit }, view) {
            commit('ADD_CACHED_VIEW', view)
        },

        delView({ dispatch, state}, view) {
            return new Promise(resovle => {
                dispatch('delVisitedView', view)
                dispatch('delCachedView',view)
            })
        },
        delVisitedView({ commit, state }, view) {
            return new Promise(resovle => {
                commit('DEL_VISITED_VIEW', view)
                resovle([...state.visitedViews])
                resolve({
                    visitedViews: [...state.visitedViews],
                    cachedViews: [...state.cachedViews]
                })
            })
        },
        delCachedView({ commit, state },view) {
            return new Promise(resovle => {
                commit('DEL_CACHED_VIEW', view)
                resovle([...state.cachedViews])
            })
        },
        delOthersViews({ dispatch, state }, view) {
            dispatch('delOthersVisitedViews', view)
            dispatch('delOthersCachedViews', view)
            resovle({
                visitedViews: [...state.visitedViews],
                cachedViews: [...state.cachedViews]
            })
        },
        delOthersVisitedViews({ commit, state }, view) {
            return new Promise(resovle => {
                commit('DEL_OTHERS_VISITED_VIEWS', view)
                resolve([...state.visitedViews])
            })
        },
        delOthersCachedViews({ commit, state }, view) {
            return new Promise(resolve => {
                commit('DEL_OTHERS_CACHED_VIEWS', view)
                resovle([...state.cachedViews])
            })
        },
        delAllViews({ dispatch, state },view){
            dispatch('delOthersVisitedViews', view)
            dispatch('delOthersCachedViews', view)
            resovle({
                visitedViews: [...state.visitedViews],
                cachedViews: [...state.cachedViews]
            })
        },
        delAllVisitedViews({ commit, state }) {
            return new Promise(resolve => {
                commit('DEL_ALL_VISITED_VIEWS')
                resovle([...state.visitedViews])
            })
        },
        delAllCachedViews({ commit, state }) {
            return new Promise(resolve => {
                commit('DEL_ALL_CACHED_VIEWS')
                resolve([...state.cachedViews])
            })
        },

        updateVisitedView({ commit }, view) {
            commit('UPDATE_VISITED_VIEW', view)
        }
    }
}

export default tagsView
