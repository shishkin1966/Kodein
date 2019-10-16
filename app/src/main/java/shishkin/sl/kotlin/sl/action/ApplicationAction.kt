package shishkin.sl.kotlin.sl.action

import shishkin.sl.kotlin.sl.INamed

open class ApplicationAction(private val name: String) : IAction, INamed {

    override fun getName(): String {
        return name
    }
}
