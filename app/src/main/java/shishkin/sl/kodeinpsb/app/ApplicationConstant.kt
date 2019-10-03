package shishkin.sl.kodeinpsb.app

import shishkin.sl.kodeinpsb.BuildConfig

class ApplicationConstant {
    companion object {
        const val APP_URL = "https://github.com/shishkin1966/Kodein"
        const val MAIL = "oleg_shishkin@mail.ru"

        const val ACTION_CLICK = BuildConfig.APPLICATION_ID + ".ACTION_CLICK"

        const val QuitOnStopSetting = "Setting.QuitOnStop"
        const val QuitOnStopSettingId = 1001
        const val ThemeSetting = "Setting.Theme"
        const val ThemeSettingId = 1010
    }
}
