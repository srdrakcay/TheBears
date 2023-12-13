package com.serdar.profile.ui

import android.view.ViewGroup
import com.serdar.common.base.BaseRecyclerViewAdapter
import com.serdar.profile.data.dto.Data


class ProfileAdapter : BaseRecyclerViewAdapter<Data, ProfileViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        return ProfileViewHolder.createFrom(parent)
    }
}