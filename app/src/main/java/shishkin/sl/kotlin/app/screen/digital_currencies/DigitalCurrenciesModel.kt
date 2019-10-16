package shishkin.sl.kotlin.app.screen.digital_currencies

import shishkin.sl.kotlin.sl.model.AbsPresenterModel

class DigitalCurrenciesModel(view: DigitalCurrenciesFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(DigitalCurrenciesPresenter(this))
    }
}
