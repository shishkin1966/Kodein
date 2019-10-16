package shishkin.sl.kotlin.app.screen.val_curs

import shishkin.sl.kotlin.sl.model.AbsPresenterModel

class ValCursModel(view: ValCursFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(ValCursPresenter(this))
    }
}
