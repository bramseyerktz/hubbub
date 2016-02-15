package com.grailsinaction
import org.springframework.security.authentication.event.AuthenticationSuccessEvent


class AuditService {
    static transactional = false //Ensures no participation in existing transaction

    //Matches method name and type to raised event topic and content
    @grails.events.Listener
    def onNewPost(Post newPost) {
        log.error "New Post from: ${newPost.user.loginId} : ${newPost.shortContent}"
    }

    @grails.events.Listener(namespace = "security")
    def onUserLogin(AuthenticationSuccessEvent loginEvent){
        log.error "Ẅe appeared to have logged in a user: ${loginEvent.authentication.principal.username}"
    }
}










/*
package com.grailsinaction

import grails.transaction.Transactional
import org.springframework.security.authentication.event.AuthenticationSuccessEvent

//@Transactional
class AuditService {

    static transactional = false
    def springSecurityService

    @grails.events.Listener
    def onNewPost(Post newPost) {
        log.error "New Post from: ${newPost.user.loginId} : ${newPost.shortContent}"
    }


    @grails.events.Listener(namespace = 'gorm')
    void onSaveOrUpdate(User user) {
        if (springSecurityService.isLoggedIn()) {
            log.error("Changes made to account ${user.loginId} by ${springSecurityService.currentUser}")
        }
    }

    @grails.events.Listener(namespace = 'gorm')
    void onSaveOrUpdate(Post post) {
        if (springSecurityService.isLoggedIn()) {
            log.error("New Post created: ${post?.content} by ${springSecurityService.currentUser}")
        }
    }

    @grails.events.Listener(namespace = 'gorm', topic = 'onSaveOrUpdate')
    void logAllAccountChanges(User user) {
        log.info "Changes made to account- ${user.profile.fullName} by ${springSecurityService.currentUser}"
    }

    @grails.events.Listener(namespace = "security")
    def onUserLogin(AuthenticationSuccessEvent loginEvent){
        log.error "We appeared to have logged in a user: ${loginEvent.authentication.principal.username}"
    }
} */