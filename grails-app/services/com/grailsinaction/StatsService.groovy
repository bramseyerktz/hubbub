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

        log.debug "New Post from: ${newPost.user.loginId}"
        redisService.incr(redisTotalsKey)

        log.debug "Total Posts at: ${redisService.get(redisTotalsKey)}"
    }
}
