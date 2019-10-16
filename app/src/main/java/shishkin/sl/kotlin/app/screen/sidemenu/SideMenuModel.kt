package shishkin.sl.kotlin.app.screen.sidemenu

import shishkin.sl.kotlin.sl.model.AbsPresenterModel

class SideMenuModel(view: SideMenuFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(SideMenuPresenter(this))
    }
}
