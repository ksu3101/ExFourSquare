package com.sw.exfoursquare.base.databinding.recyclerview

import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sw.exfoursquare.base.widgets.GridItemSpaceItemDecoration
import kotlin.math.roundToInt

/**
 * @author burkd
 * @since 2019-11-14
 */

@BindingAdapter(value = ["items", "ViewProvider", "onItemClickListener"], requireAll = false)
fun <E> bindItems(
    rv: RecyclerView,
    items: ObservableList<E>,
    viewTypeProvider: ViewTypeProvider<E>? = null,
    onItemClickListener: ((E) -> Unit)? = null
) {
    if (rv.adapter is RecyclerViewModelAdapter<*>) {
        rv.adapter?.notifyDataSetChanged()
    } else {
        val adapter = RecyclerViewModelAdapter(items, viewTypeProvider, onItemClickListener)
        rv.adapter = adapter
    }
}

@BindingAdapter(value = ["items", "itemViewResId", "onItemClickListener"], requireAll = false)
fun <E> bindItems(
    rv: RecyclerView,
    items: ObservableList<E>,
    @LayoutRes itemViewResId: Int,
    onItemClickListener: ((E) -> Unit)? = null
) {
    if (rv.adapter is RecyclerViewModelAdapter<*>) {
        rv.adapter?.notifyDataSetChanged()
    } else {
        val adapter = RecyclerViewModelAdapter(items, { itemViewResId }, onItemClickListener)
        rv.adapter = adapter
    }
}

@BindingAdapter("layoutVertical")
fun setVerticalLayoutManager(rv: RecyclerView, isVertical: Boolean) {
    rv.layoutManager = LinearLayoutManager(
        rv.context,
        if (isVertical) LinearLayoutManager.VERTICAL else LinearLayoutManager.HORIZONTAL,
        false
    )
}

@BindingAdapter(value = ["gridSpanCount", "itemGutterSpaceSize"], requireAll = false)
fun setGridLayoutManager(rv: RecyclerView, gridSpanCount: Int, itemGutterSpaceSize: Float = 0f) {
    val layoutManager = GridLayoutManager(rv.context, gridSpanCount)
    if (itemGutterSpaceSize > 0) {
        rv.addItemDecoration(
            GridItemSpaceItemDecoration(
                gridSpanCount,
                itemGutterSpaceSize.roundToInt(),
                false
            )
        )
    }
    rv.layoutManager = layoutManager
}

@BindingAdapter("fixedItemSize")
fun setFixedItemSize(rv: RecyclerView, isFixedItemSize: Boolean) {
    rv.setHasFixedSize(isFixedItemSize)
}
