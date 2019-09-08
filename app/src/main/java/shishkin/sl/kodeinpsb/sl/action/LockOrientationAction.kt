package shishkin.sl.kodeinpsb.sl.action

/**
 * Событие - закрепить ориентацию экрана
 */
class LockOrientationAction(private val orientation: Int) : IAction {
    fun getOrientation(): Int {
        return orientation
    }
}
