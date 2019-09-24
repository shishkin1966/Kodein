package shishkin.sl.kodeinpsb.app.screen.sidemenu

import shishkin.sl.kodeinpsb.app.screen.accounts.AccountsPresenter
import shishkin.sl.kodeinpsb.sl.model.AbsModel

class SideMenuModel(view: SideMenuFragment) : AbsModel(view) {
    init {
        setPresenter(SideMenuPresenter(this))
    }
}
