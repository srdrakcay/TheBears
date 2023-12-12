package com.serdar.common.validation

import androidx.annotation.StringRes
import java.util.function.Predicate

open class ValidationRule(
    @StringRes open val errorMessage: Int = com.google.android.material.R.string.error_a11y_label,
    val predicate: Predicate<String>
){
    companion object{
        const val PASSWORD_LENGTH=10
    }
}
