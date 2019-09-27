package shishkin.sl.kodeinpsb.app.screen.val_curs

import shishkin.sl.kodeinpsb.sl.presenter.AbsPresenter

class ValCursPresenter(model: ValCursModel) : AbsPresenter(model) {
    companion object {
        const val NAME = "ValCursPresenter"
    }

    override fun isRegister(): Boolean {
        return true
    }

    override fun getName(): String {
        return NAME
    }
}
