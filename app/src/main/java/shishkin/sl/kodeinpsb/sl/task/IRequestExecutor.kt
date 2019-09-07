package shishkin.sl.kodeinpsb.sl.task

import shishkin.sl.kodeinpsb.sl.request.IRequest

interface IRequestExecutor : IExecutor {

    fun execute(request: IRequest)

    fun isShutdown(): Boolean

}
