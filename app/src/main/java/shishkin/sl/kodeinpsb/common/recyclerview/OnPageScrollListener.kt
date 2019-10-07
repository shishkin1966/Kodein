package shishkin.sl.kodeinpsb.common.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class OnPageScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val listener: PageListener
) : RecyclerView.OnScrollListener() {
    private val prefetch = 50

    init {
        listener.hasData()
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = firstVisibleItemPosition + visibleItemCount
        if (lastVisibleItemPosition + prefetch >= totalItemCount) {
            listener.hasData()
            return
        }
        if (totalItemCount == visibleItemCount) {
            listener.hasData()
            return
        }
    }
}
