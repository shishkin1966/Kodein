package shishkin.sl.kotlin.app.screen.setting

import shishkin.sl.kotlin.app.ApplicationConstant
import shishkin.sl.kotlin.app.ApplicationSingleton
import shishkin.sl.kotlin.app.provider.Providers
import shishkin.sl.kotlin.app.setting.Setting
import shishkin.sl.kotlin.sl.action.*
import shishkin.sl.kotlin.sl.message.IDialogResultListener
import shishkin.sl.kotlin.sl.presenter.AbsModelPresenter
import shishkin.sl.kotlin.sl.ui.MaterialDialogExt


class SettingPresenter(model: SettingModel) : AbsModelPresenter(model),
    IDialogResultListener {
    companion object {
        const val NAME = "SettingPresenter"
        const val SettingChangedAction = "SettingChangedAction"
        const val ShowListSetting = "ShowListSetting"

        const val BackupDb = "BackupDb"
        const val RestoreDb = "RestoreDb"

        const val DBCopyEnabledAction = "DBCopyEnabledAction"
    }

    private lateinit var data: SettingsData

    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }

    override fun onAction(action: IAction): Boolean {
        if (!isValid()) return false

        if (action is DataAction<*>) {
            when (action.getName()) {
                SettingChangedAction -> {
                    (action.getData() as Setting).backup()
                    return true
                }
                ShowListSetting -> {
                    val setting = action.getData() as Setting
                    getView<SettingFragment>().addAction(
                        ShowListDialogAction(
                            id = setting.id,
                            listener = getName(),
                            title = setting.title,
                            list = setting.values
                        )
                    )
                    return true
                }
            }
        }
        if (action is ApplicationAction) {
            when (action.getName()) {
                BackupDb -> {
                    Providers.backupDb()
                    return true
                }
                RestoreDb -> {
                    Providers.restoreDb()
                    return true
                }
            }
        }

        ApplicationSingleton.instance.onError(
            getName(),
            "Unknown action:$action",
            true
        )
        return false
    }

    override fun onStart() {
        if (!::data.isInitialized) {
            data = SettingsData()
            getData()
        }
        getView<SettingFragment>().addAction(DataAction(Actions.RefreshViews, data))
        checkDbCopy();
    }

    private fun getData() {
        val list = ArrayList<Setting>()
        var setting = Setting.restore(ApplicationConstant.QuitOnStopSetting)
        if (setting == null) {
            setting = Setting(
                name = ApplicationConstant.QuitOnStopSetting,
                current = "false",
                default = "false",
                title = "Закрывать приложение при выходе",
                values = null,
                id = ApplicationConstant.QuitOnStopSettingId,
                type = Setting.TYPE_SWITCH,
                inputType = 0
            )
            setting.backup()
        }
        list.add(setting)

        setting = Setting.restore(ApplicationConstant.ThemeSetting)
        if (setting == null) {
            setting = Setting(
                name = ApplicationConstant.ThemeSetting,
                current = "светлая",
                default = "светлая",
                title = "Схема приложения",
                values = listOf("светлая", "темная"),
                id = ApplicationConstant.ThemeSettingId,
                type = Setting.TYPE_LIST,
                inputType = 0
            )
            setting.backup()
        }
        list.add(setting)

        data.settings = list
    }

    override fun onDialogResult(action: DialogResultAction) {
        if (action.getId() == ApplicationConstant.ThemeSettingId) {
            val bundle = action.getResult()
            if (MaterialDialogExt.POSITIVE == bundle.getString(MaterialDialogExt.BUTTON)) {
                val list = bundle.getStringArrayList("list")
                if (list?.size == 1) {
                    val currentValue = list[0]
                    val setting = Setting.restore(ApplicationConstant.ThemeSetting)
                    setting?.current = currentValue
                    setting?.backup()
                    getData()
                    getView<SettingFragment>().addAction(DataAction(Actions.RefreshViews, data))
                }
            }
        }
    }

    private fun checkDbCopy() {
        getView<SettingFragment>().addAction(
            DataAction(
                DBCopyEnabledAction,
                Providers.checkDbCopy()
            )
        )
    }
}
