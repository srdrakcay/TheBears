package com.serdar.home.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.serdar.home.data.dto.Data
import com.serdar.home.databinding.HomeUiComponentBinding

class CryptoPriceUiComponent @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet, defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {


    private val _binding = HomeUiComponentBinding.inflate(LayoutInflater.from(context), this, false)


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