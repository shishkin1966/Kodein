package shishkin.sl.kodeinpsb.sl.ui

interface OnBackPressListener {
    fun onBackPressed(): Boolean

    fun isTop(): Boolean
}
