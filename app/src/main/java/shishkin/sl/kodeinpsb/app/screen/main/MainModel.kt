package shishkin.sl.kodeinpsb.app.screen.main

import shishkin.sl.kodeinpsb.sl.model.AbsPresenterModel

class MainModel(view: MainActivity) : AbsPresenterModel(view) {
    init {
        setPresenter(MainPresenter(this))
    }
}
