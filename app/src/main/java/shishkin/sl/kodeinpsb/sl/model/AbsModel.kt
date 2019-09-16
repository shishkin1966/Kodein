package shishkin.sl.kodeinpsb.sl.model

import shishkin.sl.kodeinpsb.sl.presenter.IPresenter
import shishkin.sl.kodeinpsb.sl.state.IStateListener
import shishkin.sl.kodeinpsb.sl.state.IStateable
import shishkin.sl.kodeinpsb.sl.state.StateObserver


abstract class AbsModel(private val modelView: IModelView) : IModel, IStateListener {
    private var _presenter: IPresenter? = null
    private val lifecycle = StateObserver(this)

    init {
        modelView.addStateObserver(this)
    }

    override fun addStateObserver() {
        modelView.addStateObserver(this)
        if (_presenter != null) {
            modelView.addStateObserver(_presenter as IStateable)
        }
    }

    override fun <M : IModelView> getView(): M? {
        return modelView as M
    }

    override fun setPresenter(presenter: IPresenter) {
        _presenter = presenter
        addStateObserver(_presenter as IStateable)
    }

    override fun <C> getPresenter(): C? {
        if (_presenter != null) {
            return _presenter as C
        }
        return null
    }

    override fun validate(): Boolean {
        return modelView.validate()
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
