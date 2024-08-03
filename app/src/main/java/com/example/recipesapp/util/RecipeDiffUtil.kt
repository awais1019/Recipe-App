package com.example.recipesapp.util

import androidx.recyclerview.widget.DiffUtil
import com.example.recipesapp.models.Result

class RecipeDiffUtil(private val oldList: List<Result>,private val newList: List<Result>): DiffUtil.Callback() {


    override fun getOldListSize()=oldList.size
    override fun getNewListSize()=newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int)=oldList[oldItemPosition]===newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int)=oldList[oldItemPosition]==newList[newItemPosition]
}