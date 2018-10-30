package org.http4k.cloudnative.health

import org.http4k.core.HttpHandler
import org.http4k.core.Response
import org.http4k.core.Status.Companion.OK
import org.http4k.core.Status.Companion.SERVICE_UNAVAILABLE
import org.http4k.core.with
import org.http4k.lens.Header.CONTENT_TYPE

/**
 * Represents the set of operational endpoints to ensure that a particular pod is working ok.
 */
object Health {
    operator fun invoke(
        renderer: ReadinessCheckResultRenderer = DefaultReadinessCheckResultRenderer,
        checks: List<ReadinessCheck> = emptyList()): HttpHandler {
        // FIXME
//        val routes: HttpHandler = routes(
//            "/liveness" bind GET to Liveness,
//            "/readiness" bind GET to Readiness(checks, renderer)
//        )
        return { Response(OK)}
    }
}

/**
 * The Liveness check is used to determine if an app is alive.
 */
object Liveness {
    operator fun invoke(): HttpHandler = { Response(OK) }
}

/**
 * The Readiness check is used to determine if an app is prepared to receive live traffic.
 */
object Readiness {
    operator fun invoke(
        checks: List<ReadinessCheck> = emptyList(),
        renderer: ReadinessCheckResultRenderer = DefaultReadinessCheckResultRenderer
    ): HttpHandler =
        {
            val overall = when {
                checks.isNotEmpty() -> checks.map { check ->
                    try {
                        check()
                    } catch (e: Exception) {
                        Failed(check.name, e)
                    }
                }.reduce { acc, result -> acc + result }
                else -> Completed("success")
            }
            Response(if (overall.pass) OK else SERVICE_UNAVAILABLE)
                .with(CONTENT_TYPE of renderer.contentType)
                .body(renderer(overall))
        }

}