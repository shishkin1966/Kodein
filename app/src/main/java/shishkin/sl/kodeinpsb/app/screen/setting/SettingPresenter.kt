package shishkin.sl.kodeinpsb.app.screen.setting

import shishkin.sl.kodeinpsb.app.ApplicationSingleton
import shishkin.sl.kodeinpsb.app.screen.accounts.AccountsFragment
import shishkin.sl.kodeinpsb.app.setting.Setting
import shishkin.sl.kodeinpsb.sl.action.Actions
import shishkin.sl.kodeinpsb.sl.action.ApplicationAction
import shishkin.sl.kodeinpsb.sl.action.DataAction
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.presenter.AbsModelPresenter


class SettingPresenter(model: SettingModel) : AbsModelPresenter(model) {
    companion object {
        const val NAME = "SettingPresenter"
        const val SettingChangedAction = "SettingChangedAction"

        const val BackupDb = "BackupDb"
        const val RestoreDb = "RestoreDb"
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
            }
        }
        if (action is ApplicationAction) {
            when (action.getName()) {
                BackupDb -> {
                    return true
                }
                RestoreDb -> {
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
    }

    private fun getData() {
        val list = ArrayList<Setting>()
        var setting = Setting.restore(ApplicationSingleton.QuitOnStopSetting)
        if (setting == null) {
            setting = Setting(
                name = ApplicationSingleton.QuitOnStopSetting,
                current = "false",
                default = "false",
                title = "Закрывать приложение при выходе",
                values = null,
                id = -1,
                type = Setting.TYPE_SWITCH,
                inputType = 0
            )
            setting.backup()
        }
        list.add(setting)

        data.settings = list
    }
}
