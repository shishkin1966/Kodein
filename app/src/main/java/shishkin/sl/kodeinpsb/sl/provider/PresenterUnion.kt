package shishkin.sl.kodeinpsb.sl.provider

import shishkin.sl.kodeinpsb.sl.AbsSmallUnion
import shishkin.sl.kodeinpsb.sl.IProvider
import shishkin.sl.kodeinpsb.sl.presenter.IPresenter


class PresenterUnion : AbsSmallUnion<IPresenter>(),
    IPresenterUnion {
    companion object {
        const val NAME = "PresenterUnion"
    }

    override fun register(subscriber: IPresenter): Boolean {
        return if (subscriber.isValid()) {
            if (subscriber.isRegister()) {
                super.register(subscriber)
            } else true
        } else false
    }

    override fun getName(): String {
        return NAME
    }

    override fun <C : IPresenter> getPresenter(name: String): C? {
        return getSubscriber(name) as C?
    }

    override operator fun compareTo(other: IProvider): Int {
        return if (other is IPresenterUnion) 0 else 1
    }
}
