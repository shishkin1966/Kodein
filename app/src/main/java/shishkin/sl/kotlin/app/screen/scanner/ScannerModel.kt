package shishkin.sl.kotlin.app.screen.scanner

import shishkin.sl.kotlin.sl.model.AbsPresenterModel

class ScannerModel(view: ScannerFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(ScannerPresenter(this))
    }
}
