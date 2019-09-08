package shishkin.sl.kodeinpsb.sl.action

import android.widget.CompoundButton

class OnCheckedChangedAction(private val view : CompoundButton, private val isChecked : Boolean) : IAction {
    fun getView(): CompoundButton {
        return view
    }

    fun isChecked(): Boolean {
        return isChecked
    }

}
