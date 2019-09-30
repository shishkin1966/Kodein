package shishkin.sl.kodeinpsb.sl.model

import shishkin.sl.kodeinpsb.sl.presenter.IPresenter


abstract class AbsPresenterModel(modelView: IModelView) : AbsModel(modelView), IPresenterModel {
    private lateinit var presenter: IPresenter

    override fun setPresenter(presenter: IPresenter) {
        this.presenter = presenter
        super.addStateObserver(this.presenter)
    }

    override fun <C> getPresenter(): C {
        return presenter as C
    }
}
