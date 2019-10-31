package shishkin.sl.kotlin.app.screen.scanner2

import shishkin.sl.kotlin.sl.model.AbsPresenterModel

class Scanner2Model(view: Scanner2Fragment) : AbsPresenterModel(view) {
    init {
        setPresenter(Scanner2Presenter(this))
    }
}
