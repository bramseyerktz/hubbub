package com.grailsinaction

class JabberService {
    static expose = ['jms']
    static destination = "jabberInQ"
    static sendQueue = "jabberOutQ"

    def jmsService

    void onMessage(msq){
        log.debug "Got incoming jabber response from: ${msg.jabberId}"
        try {
            def profile = Profile.findByJabberAddress(msg.jabberId)
            if (profile){
                profile.user.addToPosts(new Post(content: msj.content))
            }
        } catch (t){
            log.error("Error adding post for ${msg.jabberId}", t)
        }
    }

    void sendMessage(post, jabberIds) {
        log.debug "Sending jabber message for ${post.user.userId}..."
        jmsService.sendQueueJMSMessage("jabberOutQ",
                [userId : post.user.userId,
                 content: post.content,
                 to     : jabberIds.join(",")])
    }
}

