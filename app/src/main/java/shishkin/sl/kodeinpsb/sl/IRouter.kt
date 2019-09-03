package shishkin.sl.kodeinpsb.sl

import androidx.fragment.app.Fragment

interface IRouter : ISpecialistSubscriber {
    /**
     * Переключиться на фрагмент
     *
     * @param name имя фрагмента
     */
    fun switchToFragment(name: String): Boolean

    /**
     * Показать фрагмент
     *
     * @param fragment фрагмент
     */
    fun showFragment(fragment: Fragment)

    /**
     * Показать фрагмент с allowingStateLoss
     *
     * @param fragment          фрагмент
     * @param allowingStateLoss флаг - разрешить allowingStateLoss
     */
    fun showFragment(fragment: Fragment, allowingStateLoss: Boolean)

    /**
     * Показать фрагмент
     *
     * @param fragment          фрагмент
     * @param addToBackStack    флаг - добавить в back stack
     * @param clearBackStack    флаг - очистить back stack
     * @param animate           флаг - использовать анимацию
     * @param allowingStateLoss флаг - разрешить allowingStateLoss
     */
    fun showFragment(
        fragment: Fragment, addToBackStack: Boolean, clearBackStack: Boolean,
        animate: Boolean, allowingStateLoss: Boolean
    )

    /**
     * Событие -  on back pressed.
     */
    fun onBackPressed()

    /**
     * Получить фрагмент
     *
     * @param <F> тип фрагмента
     * @param cls класс фрагмента
     * @param id  the id
     * @return фрагмент
    </F> */
    fun <F> getFragment(cls: Class<F>, id: Int): F?

    /**
     * Получить content фрагмент.
     *
     * @param <F> тип фрагмента
     * @param cls класс фрагмента
     * @return content фрагмент
    </F> */
    fun <F> getContentFragment(cls: Class<F>): F?

    /**
     * закрыть activity
     */
    fun finish()

    /**
     * Проверить наличие записей в BackStack
     *
     * @return true - записей нет
     */
    fun isBackStackEmpty(): Boolean

    /**
     * Получить кол-во записей в BackStack
     *
     * @return кол-во записей
     */
    fun getBackStackEntryCount(): Int

    /**
     * Переключится на Top фрагмент
     */
    fun switchToTopFragment()

    /**
     * Проверить наличие Top фрагмента
     *
     * @return true - Top фрагмент есть
     */
    fun hasTopFragment(): Boolean

    /**
     * Получить id контейнера, в котором будет отображен фрагмент
     *
     * @return id ресурса
     */
    fun getContentResId(): Int

}