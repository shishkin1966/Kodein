package shishkin.sl.kodeinpsb.app.screen.val_curs

import shishkin.sl.kodeinpsb.sl.model.AbsPresenterModel

class ValCursModel(view: ValCursFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(ValCursPresenter(this))
    }
}
