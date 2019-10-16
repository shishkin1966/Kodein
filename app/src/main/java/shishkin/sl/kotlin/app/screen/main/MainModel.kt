package shishkin.sl.kotlin.app.screen.main

import shishkin.sl.kotlin.sl.model.AbsPresenterModel

class MainModel(view: MainActivity) : AbsPresenterModel(view) {
    init {
        setPresenter(MainPresenter(this))
    }
}
