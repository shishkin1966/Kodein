package shishkin.sl.kodeinpsb.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar.SnackbarLayout


import androidx.coordinatorlayout.widget.CoordinatorLayout

class LinearLayoutBehavior(context: Context, attrs: AttributeSet) :
    BottomSheetBehavior<LinearLayout>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: LinearLayout,
        dependency: View
    ): Boolean {
        return dependency is SnackbarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: LinearLayout,
        dependency: View
    ): Boolean {
        if (dependency != null && child != null) {
            val translationY = Math.min(0f, dependency.translationY - dependency.height)
            child.translationY = translationY
        }
        return true
    }
}