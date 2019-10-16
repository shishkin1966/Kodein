package shishkin.sl.kotlin.sl.action

import android.content.Intent

class StartActivityForResultAction(private val intent: Intent, private val requestCode: Int) :
    IAction {

    fun getIntent(): Intent {
        return intent
    }

    fun getRequestCode(): Int {
        return requestCode
    }

}
