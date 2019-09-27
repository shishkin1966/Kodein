package shishkin.sl.kodeinpsb.sl.model

import shishkin.sl.kodeinpsb.sl.presenter.IPresenter
import shishkin.sl.kodeinpsb.sl.state.IStateListener
import shishkin.sl.kodeinpsb.sl.state.IStateable
import shishkin.sl.kodeinpsb.sl.state.StateObserver


abstract class AbsModel(private val modelView: IModelView) : IModel, IStateListener {
    private var presenter: IPresenter? = null
    private val lifecycle = StateObserver(this)

    init {
        modelView.addStateObserver(this)
    }

    override fun addStateObserver() {
        modelView.addStateObserver(this)
        if (presenter != null) {
            modelView.addStateObserver(presenter as IStateable)
        }
    }

    override fun <M : IModelView> getView(): M? {
        return modelView as M
    }

    override fun setPresenter(presenter: IPresenter) {
        this.presenter = presenter
        addStateObserver(this.presenter as IStateable)
    }

    override fun <C> getPresenter(): C? {
        if (presenter != null) {
            return presenter as C
        }
        return null
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
