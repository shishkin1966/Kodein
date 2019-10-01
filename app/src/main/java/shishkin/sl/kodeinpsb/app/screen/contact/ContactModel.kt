package shishkin.sl.kodeinpsb.app.screen.contact

import shishkin.sl.kodeinpsb.sl.model.AbsPresenterModel

class ContactModel(view: ContactFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(ContactPresenter(this))
    }
}
