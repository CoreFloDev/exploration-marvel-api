package io.coreflodev.exampleapplication.core

import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.ExternalResource
import java.net.InetAddress

class HttpServerRule : ExternalResource() {

    val mockWebServer = MockWebServer()

    override fun before() {
        mockWebServer.start(InetAddress.getByName(MOCK_SERVER_HOST), MOCK_SERVER_PORT)
    }

    override fun after() {
        mockWebServer.shutdown()
    }

    companion object {
        const val MOCK_SERVER_PORT = 9000
        const val MOCK_SERVER_HOST = "127.0.0.1"
    }
}
