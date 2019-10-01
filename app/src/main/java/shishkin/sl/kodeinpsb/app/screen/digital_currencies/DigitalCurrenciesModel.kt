package shishkin.sl.kodeinpsb.app.screen.digital_currencies

import shishkin.sl.kodeinpsb.sl.model.AbsPresenterModel

class DigitalCurrenciesModel(view: DigitalCurrenciesFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(DigitalCurrenciesPresenter(this))
    }
}
