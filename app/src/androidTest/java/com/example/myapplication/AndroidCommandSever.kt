package com.example.myapplication

//import com.example.myapplication.MessageSenderService
import io.ktor.server.netty.NettyApplicationEngine

class AndroidCommandSever(
    private val engine: NettyApplicationEngine,
//    private val messenger: MessageSenderService,
) {
    fun start() {
//        messenger.sendMessages()
        engine.start(wait = true)
    }
}