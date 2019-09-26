package shishkin.sl.kodeinpsb.app.screen.digital_currencies

import shishkin.sl.kodeinpsb.sl.model.AbsModel

class DigitalCurrenciesModel(view: DigitalCurrenciesFragment) : AbsModel(view) {
    init {
        setPresenter(DigitalCurrenciesPresenter(this))
    }
}
