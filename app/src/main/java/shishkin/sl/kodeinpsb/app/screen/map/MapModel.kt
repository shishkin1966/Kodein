package shishkin.sl.kodeinpsb.app.screen.map

import shishkin.sl.kodeinpsb.sl.model.AbsPresenterModel

class MapModel(view: MapFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(MapPresenter(this))
    }
}
