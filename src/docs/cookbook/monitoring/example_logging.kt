package cookbook.monitoring

import org.http4k.core.*
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.ResponseFilters
import org.http4k.routing.bind
import org.http4k.routing.routes
import java.time.Clock

fun main(args: Array<String>) {

    val app = routes("/{name}" bind HttpHandler { Response(OK) })

    fun logger(message: String) = println("${Clock.systemUTC().instant()} $message")

    val audit = ResponseFilters.ReportHttpTransaction { tx: HttpTransaction ->
        logger("my call to ${tx.request.uri} returned ${tx.response.status} and took ${tx.duration.toMillis()}")
    }

    val monitoredApp: HttpHandler = audit.then(app)

    monitoredApp(Request(Method.GET, "/foo"))

//    prints...
//    2017-12-04T08:38:27.499Z my call to /foo returned 200 OK and took 5
}