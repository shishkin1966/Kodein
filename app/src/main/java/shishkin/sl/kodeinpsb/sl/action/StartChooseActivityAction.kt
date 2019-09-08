package shishkin.sl.kodeinpsb.sl.action

import android.content.Intent

class StartChooseActivityAction(private val intent: Intent, private var title: String) : IAction {

    fun getIntent(): Intent {
        return intent
    }

    fun getTitle(): String {
        return title
    }
}
