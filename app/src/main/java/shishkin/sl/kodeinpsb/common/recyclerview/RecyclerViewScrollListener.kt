package shishkin.sl.kodeinpsb.common.recyclerview

import androidx.recyclerview.widget.RecyclerView

interface RecyclerViewScrollListener {

    fun idle(recyclerView: RecyclerView)

    fun scrolled(recyclerView: RecyclerView)
}
