package shishkin.sl.kotlin.sl.ui

interface OnBackPressListener {
    fun onBackPressed(): Boolean

    fun isTop(): Boolean
}
