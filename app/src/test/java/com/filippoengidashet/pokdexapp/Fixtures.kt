package com.filippoengidashet.pokdexapp

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback

object Fixtures {

    fun <T : Any> createDiffCallback(
        predicate: (odl: T, new: T) -> Boolean
    ) = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T) = predicate(oldItem, newItem)
        override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
    }

    val noOpListCallback = object : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) = Unit
        override fun onRemoved(position: Int, count: Int) = Unit
        override fun onMoved(fromPosition: Int, toPosition: Int) = Unit
        override fun onChanged(position: Int, count: Int, payload: Any?) = Unit
    }
}
