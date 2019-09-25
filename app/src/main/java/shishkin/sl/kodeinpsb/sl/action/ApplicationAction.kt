package shishkin.sl.kodeinpsb.sl.action

import shishkin.sl.kodeinpsb.sl.INamed

open class ApplicationAction(private val name: String) : IAction, INamed {

    override fun getName(): String {
        return name
    }
}
