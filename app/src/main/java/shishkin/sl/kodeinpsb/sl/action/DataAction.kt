package shishkin.sl.kodeinpsb.sl.action

import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class DataAction<T>(name : String, private val data: T? ) : ApplicationAction(name) {

    fun getData(): T? {
        return data
    }
}
