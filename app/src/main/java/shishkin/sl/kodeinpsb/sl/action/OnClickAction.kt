package shishkin.sl.kodeinpsb.sl.action

import android.view.View

class OnClickAction (private val view : View) : IAction {
    fun getView(): View {
        return view
    }
}
