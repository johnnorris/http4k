package org.http4k.core

class AsyncHttpClientTest {
    //FIXME
//    @Test
//    fun `can convert a synchronous HttpHandler to mimic the AsyncHttpClient API`() {
//
//        val handler = { _: Request -> Response(OK) }.withAsyncApi()
//
//        val latch = CountDownLatch(1)
//        handler(Request(Method.GET, "/")) {
//            assertThat(it.status, equalTo(OK))
//            latch.countDown()
//        }
//
//        latch.await()
//    }
}
