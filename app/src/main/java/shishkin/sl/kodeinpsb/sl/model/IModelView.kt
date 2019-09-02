package shishkin.sl.kodeinpsb.sl.model

import shishkin.sl.kodeinpsb.sl.IValidated
import androidx.lifecycle.Lifecycle
import android.graphics.ColorSpace.Model
import android.view.View
import androidx.annotation.IdRes
import shishkin.sl.kodeinpsb.sl.state.IStateable


interface IModelView<M:IModel> : IValidated {
    /**
     * Добавить слушателя к ModelView объекту
     *
     * @param stateable stateable объект
     */
    fun addStateObserver(stateable: IStateable)

    /**
     * Найти view в ModelView объекте
     *
     * @param <V> the type view
     * @param id  the id view
     * @return the view
    </V> */
    fun <V : View> findView(@IdRes id: Int): V?

    /**
     * Получить корневой view в ModelView объекте
     *
     * @return the view
     */
    fun getRootView(): View?

    /**
     * Закрыть ModelView объект
     */
    fun exit()

    /**
     * Получить модель
     *
     * @return модель
     */
    fun getModel(): M

    /**
     * Установить модель
     *
     * @param model модель
     */
    fun setModel(model: M)

    /**
     * Получить Lifecycle модели
     *
     * @return Lifecycle модели
     */
    fun getLifecycle(): Lifecycle
}
