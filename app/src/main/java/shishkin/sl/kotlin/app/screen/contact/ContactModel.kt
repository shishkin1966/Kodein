package shishkin.sl.kotlin.app.screen.contact

import shishkin.sl.kotlin.sl.model.AbsPresenterModel

class ContactModel(view: ContactFragment) : AbsPresenterModel(view) {
    init {
        setPresenter(ContactPresenter(this))
    }
}
