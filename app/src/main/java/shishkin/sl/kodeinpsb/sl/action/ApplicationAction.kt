package shishkin.sl.kodeinpsb.sl.action

open class ApplicationAction(private val name: String) : IAction {

    fun getName() : String {
        return name
    }
}
