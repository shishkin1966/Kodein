package shishkin.sl.kotlin.app.screen.accounts

import shishkin.sl.kotlin.sl.model.AbsPresenterModel

class AccountsModel(view: AccountsFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(AccountsPresenter(this))
    }
}
