package shishkin.sl.kodeinpsb.app.data

import com.google.gson.Gson
import shishkin.sl.kodeinpsb.app.ApplicationSingleton


abstract class AbsEntity {
    fun toJson(): String {
        return Gson().toJson(this)
    }

    fun <T> fromJson(json: String?, klass: Class<T>): T? {
        if (json == null) return null

        try {
            return Gson().fromJson(json, klass)
        } catch (e: Exception) {
            ApplicationSingleton.instance.onError(klass.simpleName, e)
        }
        return null
    }
}
