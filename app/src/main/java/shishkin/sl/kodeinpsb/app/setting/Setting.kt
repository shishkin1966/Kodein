package shishkin.sl.kodeinpsb.app.setting


import android.text.InputType
import com.google.gson.annotations.SerializedName
import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.common.PreferencesUtils
import shishkin.sl.kodeinpsb.sl.provider.ApplicationProvider
import java.io.Serializable


class Setting : Serializable {

    companion object {
        const val TYPE_LIST = 0
        const val TYPE_SWITCH = 1
        const val TYPE_TEXT = 2
        const val TYPE_COLOR = 3
        const val TYPE_EDIT = 4

        @JvmStatic
        fun restore(name: String): Setting? {
            val json = PreferencesUtils.getString(ApplicationProvider.appContext, name, null)
            if (json == null) {
                return null
            }
            return ApplicationSingleton.instance.getCache().fromJson(json, Setting::class.java)
        }
    }

    constructor(name: String) {
        this.name = name
    }

    constructor(
        values: List<String>?,
        current: String?,
        default: String?,
        name: String,
        title: String?,
        id: Int,
        type: Int,
        inputType: Int
    ) {
        this.values = values
        this.current = current
        this.default = default
        this.name = name
        this.title = title
        this.id = id
        this.type = type
        this.inputType = inputType
    }

    @SerializedName("values")
    var values: List<String>? = null

    @SerializedName("current")
    var current: String? = null

    @SerializedName("default")
    var default: String? = null

    @SerializedName("name")
    var name: String

    @SerializedName("title")
    var title: String? = null

    @SerializedName("id")
    var id: Int = 0

    @SerializedName("type")
    var type = 0

    @SerializedName("inputType")
    var inputType = InputType.TYPE_CLASS_NUMBER


    fun backup() {
        PreferencesUtils.putString(
            ApplicationProvider.appContext,
            name,
            ApplicationSingleton.instance.getCache().toJson(this).toString()
        )
    }
}
