package shishkin.sl.kotlin.sl.task

import shishkin.sl.kotlin.sl.request.IRequest

interface IRequestExecutor : IExecutor {

    fun execute(request: IRequest)

    fun isShutdown(): Boolean

}
