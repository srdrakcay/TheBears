package com.serdar.common.validation

import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

fun TextInputEditText.validationRule(
    rules:List<ValidationRule>,
    callBack:((TextInputEditTextUiModel)->Unit)?=null
):Boolean{
    val textInputLayout=this.parent.parent as TextInputLayout
    val viewEnable = textInputLayout.isEnabled


    this.doAfterTextChanged {
        textInputLayout.error=null
        textInputLayout.isErrorEnabled=false
    }

    val validateText=this.text.toString().trim()

    for (i in rules.indices){
        val isNotValid=rules[i].predicate.test(validateText)
        val message=rules[i].errorMessage
        if (isNotValid){
            if (!viewEnable) textInputLayout.isEnabled=true
            textInputLayout.error=context.resources.getString(message)
            callBack?.invoke(TextInputEditTextUiModel(isEnabled=viewEnable))
            return false
        }else{
            continue
        }

    }
    return true
}