package com.example.photo_vk.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import com.example.photo_vk.R

fun vkErrorHandler(
    context: Context,
    errorLabel: TextView,
    code: Int?,
    Ex: Exception
) {
    when (code) {
        ErrorTypes.TOKEN_EXPIRED -> errorLabel.text =
            context.getString(R.string.token_error)
        ErrorTypes.TOKEN_INVALID -> errorLabel.text =
            context.getString(R.string.token_error)
        ErrorTypes.PERMISSION_DENIED -> errorLabel.text =
            context.getString(R.string.permission_denied_error)
        ErrorTypes.BLOCKED -> errorLabel.text = context.getString(R.string.user_blocked_error)
        ErrorTypes.INCORRECT_PARAM -> errorLabel.text =
            context.getString(R.string.incorrect_param_error)
        ErrorTypes.ALBUM_PERMISSION_DENIED -> errorLabel.text =
            context.getString(R.string.permission_denied_error)
        else -> errorLabel.text = context.getString(R.string.try_again_error)
    }
    errorLabel.visibility = View.VISIBLE
    Ex.localizedMessage?.let { Log.e("Error", it) }
}