package com.serdar.home.ui

import android.view.ViewGroup
import com.serdar.common.base.BaseRecyclerViewAdapter
import com.serdar.home.data.dto.Data


class HomeAdapter : BaseRecyclerViewAdapter<Data, HomeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder.createFrom(parent)
    }
}