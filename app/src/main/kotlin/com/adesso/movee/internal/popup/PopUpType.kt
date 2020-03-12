package com.adesso.movee.internal.popup

import androidx.annotation.Keep
import com.adesso.movee.R

@Keep
enum class PopUpType(val iconRes: Int, val bgRes: Int) {
    INFO(android.R.drawable.ic_dialog_info, R.drawable.background_popup_info),
    WARNING(android.R.drawable.stat_sys_warning, R.drawable.background_popup_warning),
    ERROR(android.R.drawable.stat_notify_error, R.drawable.background_popup_error_round)
}
