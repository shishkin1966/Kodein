package shishkin.sl.kodeinpsb.sl.specialist

import shishkin.sl.kodeinpsb.sl.ISmallUnion
import shishkin.sl.kodeinpsb.sl.presenter.IPresenter

interface IPresenterUnion : ISmallUnion<IPresenter> {
    /**
     * Получить presenter
     *
     * @param name имя презентера
     * @return презентер
     */
    fun <C : IPresenter> getPresenter(name: String): C?

}
