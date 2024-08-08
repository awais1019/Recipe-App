package com.example.recipesapp.util

import androidx.recyclerview.widget.DiffUtil
import com.example.recipesapp.models.Result

class RecipeDiffUtil<T>(private val oldList: List<T>,private val newList: List<T>): DiffUtil.Callback() {


    override fun getOldListSize()=oldList.size
    override fun getNewListSize()=newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int)=oldList[oldItemPosition]===newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int)=oldList[oldItemPosition]==newList[newItemPosition]
}