package shishkin.sl.kodeinpsb.sl.presenter

import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.action.IAction
import shishkin.sl.kodeinpsb.sl.message.IMessage
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.model.IModelView
import shishkin.sl.kodeinpsb.sl.specialist.ApplicationSpecialist
import shishkin.sl.kodeinpsb.sl.specialist.IMessengerUnion
import shishkin.sl.kodeinpsb.sl.specialist.MessengerUnion
import shishkin.sl.kodeinpsb.sl.specialist.PresenterUnion
import shishkin.sl.kodeinpsb.sl.state.State
import shishkin.sl.kodeinpsb.sl.state.StateObserver
import java.util.*


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
