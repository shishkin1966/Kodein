package shishkin.sl.kotlin.app.screen.map

import shishkin.sl.kotlin.sl.model.AbsPresenterModel

class MapModel(view: MapFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(MapPresenter(this))
    }
}
