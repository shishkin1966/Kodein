package shishkin.sl.kodeinpsb.app.screen.create_account

import shishkin.sl.kodeinpsb.sl.model.AbsPresenterModel

class CreateAccountModel(view: CreateAccountFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(CreateAccountPresenter(this))
    }
}
