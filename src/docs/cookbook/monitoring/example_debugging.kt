package cookbook.monitoring

import org.http4k.core.*
import org.http4k.core.Status.Companion.OK
import org.http4k.filter.DebuggingFilters

fun main(args: Array<String>) {

    val app = HttpHandler { Response(OK).body("hello there you look nice today") }

    val debuggedApp = DebuggingFilters.PrintRequestAndResponse().then(app)

    debuggedApp(Request(Method.GET, "/foobar").header("Accepted", "my-great-content/type"))
}