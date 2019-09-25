package shishkin.sl.kodeinpsb.app.screen.map

import shishkin.sl.kodeinpsb.sl.model.AbsModel

class MapModel(view: MapFragment) : AbsModel(view) {
    init {
        setPresenter(MapPresenter(this))
    }
}
