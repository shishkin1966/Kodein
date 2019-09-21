package shishkin.sl.kodeinpsb.sl.action

class PermissionAction(private val permission: String) : IAction {
    private var _listener: String? = null
    private var _helpMessage: String? = null

    constructor(permission: String, listener: String, helpMessage: String) : this(permission) {
        _listener = listener
        _helpMessage = helpMessage
    }

    fun getListener(): String? {
        return _listener
    }

    fun getPermission(): String {
        return permission
    }

    fun getHelpMessage(): String? {
        return _helpMessage
    }


}
