package com.grailsinaction

//import grails.transaction.Transactional

//@Transactional
class StatsService {
    static transactional = false

    def redisService

    @grails.events.Listener
    void onNewPost(Post newPost) {
        String dateToday = new Date().format("yy-MM-dd")
        String redisTotalsKey = "daily.stat.totalPosts.${dateToday}"

        //log.debug "New Post from: ${newPost.user.loginId}"
        redisService.incr(redisTotalsKey)

        String redisTotalsByUserKey = "daily.stat.totalsByUser.${dateToday}"
        redisService.zincrby(redisTotalsByUserKey, 1, newPost.user.loginId)
        int usersPostsToday = redisService.zscore(redisTotalsByUserKey, newPost.user.loginId)

        log.debug("Incremented daily stat for ${newPost.user.loginId} to ${usersPostsToday}")

        log.debug "Total Posts at: ${redisService.get(redisTotalsKey)}"
    }

    def getTodaysTopPosters(){
        String dateToday = new Date().format("yy-MM-dd")
        String redisTotalsByUserKey = "daily.stat.totalsByUser.${dateToday}"
        def tuples = redisService.zrevrangeWithScores(redisTotalsByUserKey, 0, 1000)
            tuples.each {tuple -> log.debug("Posts for ${tuple.element} -> ${tuple.score}")}
        return tuples
    }
}
