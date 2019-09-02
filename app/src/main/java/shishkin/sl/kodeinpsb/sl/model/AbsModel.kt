package shishkin.sl.kodeinpsb.sl.model

import shishkin.sl.kodeinpsb.sl.presenter.IPresenter
import shishkin.sl.kodeinpsb.sl.state.IStateListener
import shishkin.sl.kodeinpsb.sl.state.IStateable
import shishkin.sl.kodeinpsb.sl.state.StateObserver


class AbsModel : IModel, IStateListener {
    private var modelView: IModelView? = null
    private var presenter: IPresenter<IModel>? = null
    private val lifecycle = StateObserver(this)

    constructor(view: IModelView) {
        modelView = view
        modelView?.addStateObserver(this)
    }

    override fun addStateObserver() {
        if (modelView != null) {
            modelView?.addStateObserver(this)
            if (presenter != null) {
                modelView?.addStateObserver(presenter as IStateable)
            }
        }
    }

    override fun <V : IModelView> getView(): V? {
        return modelView as V
    }

    override fun setView(view: IModelView) {
        modelView = view
    }

    override fun setPresenter(presenter: IPresenter<IModel>) {
        this.presenter = presenter
        if (this.presenter != null) {
            addStateObserver(this.presenter as IStateable)
        }
    }

    override fun <C> getPresenter(): C? {
        return presenter as C
    }

    override fun validate(): Boolean {
        if (modelView != null) {
            return modelView!!.validate()
        } else {
            return false
        }
    }

    override fun addStateObserver(stateable: IStateable) {
        if (modelView != null) {
            modelView?.addStateObserver(stateable)
        }
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
