package com.grailsinaction

//import grails.plugin.spock.IntegrationSpec
import grails.test.spock.IntegrationSpec

class JabberServiceIntegrationSpec extends IntegrationSpec {
    def jabberService
    def jmsService

    def jmsOutputQueue = "jabberOutQ"

    static transactional = false

    def "First send to a queue"() {
        given: "Some sample queue data"
        def post = [user: [userId: 'chuck_norris'],
                    content: 'is backstroking across the atlantic']
        def jabberIds = ["glen@grailsinaction.com",
                         "peter@grailsinaction.com"]
        expect:
        jabberService.sendMessage(post, jabberIds)
    }

    def "Send message to the jabber queue"(){
        given: "some sample queue data"
        def post = [user: [userId: 'chuck_norris'],
                    content: 'is backstroking across the atlantic']
        def jabberIds = ["glen@grailsinaction.com",
                        "peter@grailsinaction.com"]
        def msgListBeforeSend = jmsService.browse(jabberService.sendQueue)

        when:
        jabberService.sendMessage(post, jabberIds)

        then:
        jmsService.browse(jabberService.sendQueue).size() == msgListBeforeSend.size() + 1
    }
}