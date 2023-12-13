package com.serdar.profile.ui

import android.view.ViewGroup
import com.serdar.common.base.BaseViewHolder
import com.serdar.common.extensions.inflateAdapterItem
import com.serdar.profile.data.dto.Data
import com.serdar.profile.databinding.ItemCryptoLayoutBinding

class ProfileViewHolder(
    private val binding: ItemCryptoLayoutBinding,
) :
    BaseViewHolder<Data>(binding.root) {
    companion object {
        fun createFrom(
            parent: ViewGroup,
        ) =
            ProfileViewHolder(
                parent.inflateAdapterItem(ItemCryptoLayoutBinding::inflate),
            )
    }

    override fun onBind(data: Data) {
        binding.cryptoPrice.setProductData(data)

    }


}