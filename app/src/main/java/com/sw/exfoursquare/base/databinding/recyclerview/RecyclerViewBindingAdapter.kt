package com.sw.exfoursquare.base.databinding.recyclerview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * @author burkd
 * @since 2019-11-14
 */

@BindingAdapter(value = ["items", "ViewProvider", "onItemClickListener"], requireAll = false)
fun <E> bindItems(
    rv: RecyclerView,
    items: List<E>,
    viewProvider: ViewProvider<E>? = null,
    onItemClickListener: ((E) -> Unit)? = null
) {
    val adapter = RecyclerViewModelAdapter(items, viewProvider, onItemClickListener)
    rv.adapter = adapter
}

@BindingAdapter("layoutVertical")
fun setLayoutVertical(rv: RecyclerView, isVertical: Boolean) {
    rv.layoutManager = LinearLayoutManager(
        rv.context,
        if (isVertical) LinearLayoutManager.VERTICAL else LinearLayoutManager.HORIZONTAL,
        false
    )
}

@BindingAdapter("fixedItemSize")
fun setFixedItemSize(rv: RecyclerView, isFixedItemSize: Boolean) {
    rv.setHasFixedSize(isFixedItemSize)
}
