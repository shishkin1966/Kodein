package shishkin.sl.kotlin.app.screen.create_account

import shishkin.sl.kotlin.sl.model.AbsPresenterModel

class CreateAccountModel(view: CreateAccountFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(CreateAccountPresenter(this))
    }
}
