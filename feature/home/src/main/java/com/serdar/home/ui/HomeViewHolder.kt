package com.serdar.home.ui

import android.view.ViewGroup
import com.serdar.common.base.BaseViewHolder
import com.serdar.common.extensions.inflateAdapterItem
import com.serdar.home.data.dto.Data
import com.serdar.home.databinding.ItemCryptoLayoutBinding

class HomeViewHolder(
    private val binding: ItemCryptoLayoutBinding,
) :
    BaseViewHolder<Data>(binding.root) {
    companion object {
        fun createFrom(
            parent: ViewGroup,
        ) =
            HomeViewHolder(
                parent.inflateAdapterItem(ItemCryptoLayoutBinding::inflate),
            )
    }

    override fun onBind(data: Data) {
        binding.cryptoPrice.setProductData(data)

    }


}