package shishkin.sl.kodeinpsb.app.screen.val_curs

import shishkin.sl.kodeinpsb.sl.presenter.AbsModelPresenter

class ValCursPresenter(model: ValCursModel) : AbsModelPresenter(model) {
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
