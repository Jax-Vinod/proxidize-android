package com.legacy.android

data class ProxyServer(val host: String, val port: String, val token: String) {

    companion object {
        const val HOST = "103.172.92.43"
        const val SERVER_PORT = "2000"
        const val TOKEN = "X88R9#t7"

        fun default() = ProxyServer(HOST, SERVER_PORT, TOKEN)
    }
}
