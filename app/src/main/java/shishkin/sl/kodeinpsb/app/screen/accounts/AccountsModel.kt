package shishkin.sl.kodeinpsb.app.screen.accounts

import shishkin.sl.kodeinpsb.sl.model.AbsPresenterModel

class AccountsModel(view: AccountsFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(AccountsPresenter(this))
    }
}
