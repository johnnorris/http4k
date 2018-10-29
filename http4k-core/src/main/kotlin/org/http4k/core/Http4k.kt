package org.http4k.core

import org.http4k.routing.RoutingHttpHandler

interface HttpHandler {
    operator fun invoke(request: Request): Response

    companion object {
        operator fun invoke(fn: (Request) -> Response): HttpHandler = object : HttpHandler {
            override fun invoke(request: Request): Response = fn(request)
        }
    }
}

interface Filter : (HttpHandler) -> HttpHandler {
    override operator fun invoke(next: HttpHandler): HttpHandler

    companion object {
        operator fun invoke(fn: (HttpHandler) -> HttpHandler): Filter = object : Filter {
            override operator fun invoke(next: HttpHandler): HttpHandler = fn(next)
        }
    }
}

val Filter.Companion.NoOp: Filter get() = Filter { next -> HttpHandler { next(it) } }

fun Filter.then(next: Filter): Filter = Filter { this(next(it)) }

fun Filter.then(next: suspend (Request) -> Response): HttpHandler = HttpHandler { this(next as HttpHandler)(it) }
fun Filter.then(next: HttpHandler): HttpHandler = HttpHandler { this(next)(it) }

fun Filter.then(routingHttpHandler: RoutingHttpHandler): RoutingHttpHandler = routingHttpHandler.withFilter(this)