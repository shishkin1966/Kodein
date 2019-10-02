package shishkin.sl.kodeinpsb.app.screen.setting

import shishkin.sl.kodeinpsb.sl.model.AbsPresenterModel

class SettingModel(view: SettingFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(SettingPresenter(this))
    }
}
