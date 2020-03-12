package com.adesso.movee.base

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.FrameLayout
import androidx.databinding.ViewDataBinding
import com.adesso.movee.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

abstract class BaseFullScreenBottomSheetFragment<VM : BaseAndroidViewModel, B : ViewDataBinding> :
    BaseBottomSheetDialogFragment<VM, B>() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        return dialog.apply {
            setOnShowListener { dialogInterface ->
                val bottomSheetDialog = dialogInterface as BottomSheetDialog
                val bottomSheet =
                    dialogInterface.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
                bottomSheetDialog.window?.setDimAmount(0f)
                setupFullHeight(bottomSheetDialog)
                BottomSheetBehavior.from(bottomSheet).apply {
                    state = BottomSheetBehavior.STATE_EXPANDED

                    setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                        override fun onStateChanged(bottomSheet: View, newState: Int) {
                            if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                                state = BottomSheetBehavior.STATE_EXPANDED
                            }
                        }

                        override fun onSlide(bottomSheet: View, slideOffset: Float) {
                            state = BottomSheetBehavior.STATE_EXPANDED
                        }
                    })
                }
            }
        }
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
        val layoutParams = bottomSheet.layoutParams
        val windowHeight = getWindowHeight()
        layoutParams?.let {
            it.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}
