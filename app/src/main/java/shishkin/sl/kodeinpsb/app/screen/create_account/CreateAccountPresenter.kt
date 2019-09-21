package shishkin.sl.kodeinpsb.app.screen.create_account

import shishkin.sl.kodeinpsb.sl.presenter.AbsPresenter


class CreateAccountPresenter(model: CreateAccountModel) : AbsPresenter(model) {
    companion object {
        const val NAME = "CreateAccountPresenter"
    }

    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }
}
