package shishkin.sl.kodeinpsb.sl.presenter

import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.model.IModelView


abstract class AbsModelPresenter() : AbsPresenter(), IModelPresenter {
    private lateinit var model: IModel

    constructor(model: IModel) : this() {
        this.model = model
    }

    override fun <M : IModel> getModel(): M {
        return model as M
    }

    override fun <C : IModelView> getView(): C {
        return model.getView()
    }
}
