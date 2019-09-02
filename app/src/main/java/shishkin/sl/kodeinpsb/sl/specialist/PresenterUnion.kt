package shishkin.sl.kodeinpsb.sl.specialist

import shishkin.sl.kodeinpsb.sl.AbsSmallUnion
import shishkin.sl.kodeinpsb.sl.ISpecialist
import shishkin.sl.kodeinpsb.sl.model.IModel
import shishkin.sl.kodeinpsb.sl.presenter.IPresenter


class PresenterUnion : AbsSmallUnion<IPresenter<IModel>>(), IPresenterUnion {
    companion object {
        const val NAME = "PresenterUnion"
    }

    override fun register(subscriber: IPresenter<IModel>): Boolean {
        return if (subscriber.validate()) {
            if (subscriber.isRegister()) {
                super.register(subscriber!!)
            } else true
        } else false
    }

    override fun getName(): String {
        return NAME
    }

    override fun <C> getPresenter(name: String): C? {
        return getSubscriber(name) as C?
    }

    override operator fun compareTo(other: ISpecialist): Int {
        return if (other is IPresenterUnion) 0 else 1
    }


}
