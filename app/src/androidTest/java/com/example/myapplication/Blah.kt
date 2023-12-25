package com.example.myapplication

//import MessageSenderService
import io.ktor.server.netty.NettyApplicationEngine

class Blah(
    private val engine: NettyApplicationEngine,
//    private val messenger: MessageSenderService,
) {
    fun start() {
//        messenger.sendMessages()
        engine.start(wait = true)
    }
}