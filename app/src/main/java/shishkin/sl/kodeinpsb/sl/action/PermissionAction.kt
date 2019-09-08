package shishkin.sl.kodeinpsb.sl.action

class PermissionAction(private val permission: String) : IAction {
    private var listener: String? = null
    private var helpMessage: String? = null

    constructor(permission: String, listener: String, helpMessage: String) : this(permission) {
        this.listener = listener
        this.helpMessage = helpMessage
    }

    fun getListener(): String? {
        return listener
    }

    fun getPermission(): String {
        return permission
    }

    fun getHelpMessage(): String? {
        return helpMessage
    }


}
