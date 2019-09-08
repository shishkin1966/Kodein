package shishkin.sl.kodeinpsb.sl.action

import android.os.Bundle


class DialogResultAction(private val result: Bundle = Bundle(), private val id: Int) : IAction {

    fun getResult(): Bundle {
        return result
    }

    fun getId(): Int {
        return id
    }

}
