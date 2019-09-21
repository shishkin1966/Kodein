package shishkin.sl.kodeinpsb.app.screen.create_account

import shishkin.sl.kodeinpsb.sl.model.AbsModel

class CreateAccountModel(view: CreateAccountFragment) : AbsModel(view) {
    init {
        setPresenter(CreateAccountPresenter(this))
    }
}
