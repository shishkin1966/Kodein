package shishkin.sl.kodeinpsb.app.observe


import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import shishkin.sl.kodeinpsb.common.Debounce
import java.lang.ref.WeakReference
import java.util.*

open class EditTextObservable(observer: Observer, val view: EditText, delay: Long = 500) :
    Observable() {
    private var delay: Long = 500
    private val debounce: Debounce
    private val observer: WeakReference<Observer>
    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {
            this@EditTextObservable.afterTextChanged(s)
        }
    }

    init {
        this.delay = delay
        this.view.addTextChangedListener(textWatcher)
        this.observer = WeakReference(observer)
        this.debounce = object : Debounce(delay) {
            override fun run() {
                if (this@EditTextObservable.observer.get() != null) {
                    this@EditTextObservable.observer.get()
                        ?.update(this@EditTextObservable, view.text.toString())
                } else {
                    finish()
                }
            }
        }
    }

    fun finish() {
        view.removeTextChangedListener(textWatcher)
        debounce.finish()
    }

    open fun afterTextChanged(s: Editable) {
        setChanged()
        debounce.onEvent()
    }

}
