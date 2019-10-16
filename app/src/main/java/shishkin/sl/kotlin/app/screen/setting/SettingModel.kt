package shishkin.sl.kotlin.app.screen.setting

import shishkin.sl.kotlin.sl.model.AbsPresenterModel

class SettingModel(view: SettingFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(SettingPresenter(this))
    }
}
