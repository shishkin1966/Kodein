package shishkin.sl.kotlin.sl.provider

import shishkin.sl.kotlin.sl.IProvider
import shishkin.sl.kotlin.sl.request.IRequest

interface IRequestProvider : IProvider {
    /**
     * Выполнить запрос
     *
     * @param request запрос
     */
    fun request(request: IRequest)
}
