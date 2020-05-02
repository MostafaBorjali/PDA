package com.okala.test.utils.customview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.*
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("app:refreshing")
fun setRefreshing(swipeRefreshLayout: SwipeRefreshLayout, isrefresh: Boolean) {
    swipeRefreshLayout.isRefreshing = isrefresh
}

@BindingAdapter("app:direction")
fun setDirection(recyclerView: RecyclerView, orientation: String) {

    if (orientation == "vertical") {
        recyclerView.itemAnimator = DefaultItemAnimator()
        val llm = LinearLayoutManager(recyclerView.context)
        recyclerView.layoutManager = llm
        recyclerView.isNestedScrollingEnabled = false
        val decoration = DividerItemDecoration(recyclerView.context, 0)
        recyclerView.addItemDecoration(decoration)
    }

    if (orientation == "horizontal") {
        val layoutManager = LinearLayoutManager(
            recyclerView.context,
            LinearLayoutManager.HORIZONTAL, true
        )
        recyclerView.layoutManager = layoutManager
//
    }

    if (orientation == "gridView") {
        recyclerView.itemAnimator = DefaultItemAnimator()
        val llm = LinearLayoutManager(recyclerView.context)
        recyclerView.layoutManager = llm
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, 2)
    }
}
