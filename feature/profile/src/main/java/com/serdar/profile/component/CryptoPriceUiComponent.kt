package com.serdar.profile.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.serdar.profile.data.dto.Data
import com.serdar.profile.databinding.ProfileUiComponentBinding

class CryptoPriceUiComponent @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet, defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {


    private val _binding = ProfileUiComponentBinding.inflate(LayoutInflater.from(context), this, false)


    init {

        addView(_binding.root)

    }

    fun setProductData(cryptoPriceData: Data) {
        with(_binding) {
            txtCryptoPrice.text = cryptoPriceData.priceUsd
            txtCryptoSymbol.text = cryptoPriceData.symbol

        }

    }


}