package shishkin.sl.kodeinpsb.sl.action

import android.content.Intent

class StartActivityAction(private val intent : Intent) : IAction {

    fun getIntent(): Intent {
        return intent
    }

}
