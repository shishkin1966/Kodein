package shishkin.sl.kodeinpsb.sl.action

import android.os.Bundle



class DialogResultAction() : AbsAction() {
    private var result: Bundle = Bundle()
    private var id = -1

    constructor(result: Bundle, id: Int): this() {
        this.id = id
        this.result = result
    }

    fun getResult(): Bundle {
        return result
    }

    fun getId(): Int {
        return id
    }

}