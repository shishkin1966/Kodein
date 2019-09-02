package shishkin.sl.kodeinpsb.sl.model

import shishkin.sl.kodeinpsb.sl.presenter.IPresenter
import shishkin.sl.kodeinpsb.sl.state.IStateListener
import shishkin.sl.kodeinpsb.sl.state.IStateable
import shishkin.sl.kodeinpsb.sl.state.StateObserver


abstract class AbsModel<M> : IModel<M>, IStateListener {
    private var modelView: M? = null
    private var presenter: IPresenter? = null
    private val lifecycle = StateObserver(this)

    constructor(view:IModelView<*>) {
        modelView = view as M
        (modelView as IModelView<*>).addStateObserver(this)
    }

    override fun addStateObserver() {
        if (modelView != null) {
            (modelView as IModelView<*>).addStateObserver(this)
            if (presenter != null) {
                (modelView as IModelView<*>).addStateObserver(presenter as IStateable)
            }
        }
    }

    override fun getView(): M? {
        return modelView as M
    }

    override fun setView(view: M) {
        modelView = view
    }

    override fun setPresenter(presenter: IPresenter) {
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
            return (modelView as IModelView<*>).validate()
        } else {
            return false
        }
    }

    override fun addStateObserver(stateable: IStateable) {
        if (modelView != null) {
            (modelView as IModelView<*>)?.addStateObserver(stateable)
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
