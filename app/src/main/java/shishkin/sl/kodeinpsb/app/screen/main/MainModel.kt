package shishkin.sl.kodeinpsb.app.screen.main

import shishkin.sl.kodeinpsb.sl.model.AbsModel

class MainModel(view: MainActivity) : AbsModel(view) {
    init {
        setPresenter(MainPresenter(this))
    }
}
