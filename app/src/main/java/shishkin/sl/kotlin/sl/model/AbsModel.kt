package shishkin.sl.kotlin.sl.model

import shishkin.sl.kotlin.sl.state.IStateListener
import shishkin.sl.kotlin.sl.state.IStateable
import shishkin.sl.kotlin.sl.state.StateObserver


abstract class AbsModel(private val modelView: IModelView) : IModel, IStateListener {

    private val lifecycle = StateObserver(this)

    init {
        modelView.addStateObserver(this)
    }

    override fun <M : IModelView> getView(): M {
        return modelView as M
    }

    override fun isValid(): Boolean {
        return modelView.isValid()
    }

    override fun addStateObserver(stateable: IStateable) {
        modelView.addStateObserver(stateable)
    }

    override fun getState(): Int {
        return lifecycle.getState()
    }

    override fun setState(state: Int) {
        lifecycle.setState(state)
    }

    override fun onCreateView() {}

    override fun onReadyView() {}

    override fun onDestroyView() {}

}
