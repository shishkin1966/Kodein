package shishkin.sl.kodeinpsb.app.screen.sidemenu

import shishkin.sl.kodeinpsb.sl.model.AbsPresenterModel

class SideMenuModel(view: SideMenuFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(SideMenuPresenter(this))
    }
}
