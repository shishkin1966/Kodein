package shishkin.sl.kotlin.sl.provider

import shishkin.sl.kotlin.sl.ISmallUnion
import shishkin.sl.kotlin.sl.presenter.IPresenter

interface IPresenterUnion : ISmallUnion<IPresenter> {
    /**
     * Получить presenter
     *
     * @param name имя презентера
     * @return презентер
     */
    fun <C : IPresenter> getPresenter(name: String): C?

}
