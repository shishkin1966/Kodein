package shishkin.sl.kodeinpsb.app.observe

import android.text.Editable
import android.widget.EditText
import shishkin.sl.kodeinpsb.common.left
import shishkin.sl.kodeinpsb.common.toLong
import shishkin.sl.kodeinpsb.common.token
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*


class EditTextLongObservable(observer: Observer, view: EditText, delay: Long = 500) :
    EditTextObservable(observer, view, delay) {
    private var _isEditing = false

    override fun afterTextChanged(s: Editable) {
        if (_isEditing) return
        _isEditing = true

        var str = s.toString().token("\\.", 1)
        str = str.left(10)
        val format = DecimalFormat("###,###")
        val customSymbol = DecimalFormatSymbols()
        customSymbol.decimalSeparator = '.'
        customSymbol.groupingSeparator = ' '
        format.decimalFormatSymbols = customSymbol
        val ss = format.format(str.toLong())
        if (ss != view.text.toString()) {
            view.setText(ss)
            view.setSelection(ss.length)
        }
        super.afterTextChanged(s)
        _isEditing = false
    }
}
