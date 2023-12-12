package com.serdar.common.validation

import androidx.annotation.StringRes

class PasswordRule (
    @StringRes override val errorMessage:Int= com.google.android.material.R.string.error_a11y_label
):ValidationRule(predicate = {
    val password =it
    password.length != PASSWORD_LENGTH
})