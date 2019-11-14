package com.sw.exfoursquare.base.databinding.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.sw.common.extensions.getElement
import com.sw.common.extensions.ifNotNull
import com.sw.exfoursquare.BR

typealias ViewTypeProvider<T> = ((itemByPosition: T) -> Int) // return @LayoutResId!!

/**
 * @author burkd
 * @since 2019-11-14
 */
class RecyclerViewModelAdapter<E>(
    private var items: List<E> = emptyList(),
    private val viewTypeProvider: ViewTypeProvider<E>? = null,
    private val onItemClickListener: ((E) -> Unit)? = null
) : RecyclerView.Adapter<DatabindingViewHolder>() {

    fun setItems(items: List<E>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatabindingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false)
        val bindingViewHoler = DatabindingViewHolder(view)
        onItemClickListener?.let { listener ->
            bindingViewHoler.itemView.setOnClickListener {
                listener(getItem(bindingViewHoler.adapterPosition))
            }
        }
        return bindingViewHoler
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DatabindingViewHolder, position: Int) {
        val item = getItem(position)
        holder.binder?.let {
            it.setVariable(BR.vm, item)
            it.executePendingBindings()
        }
    }

    @LayoutRes
    override fun getItemViewType(position: Int): Int {
        return viewTypeProvider.ifNotNull({ it(getItem(position)) }, { 0 })
    }

    fun getItem(pos: Int): E {
        return items.getElement(pos)
            ?: throw IndexOutOfBoundsException("RecyclerView 아이템 의 [$pos] 항목이 존재 하지 않습니다. ")
    }
}

class DatabindingViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    val binder: ViewDataBinding? = DataBindingUtil.bind(itemView)
}

