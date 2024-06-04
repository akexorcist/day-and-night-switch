package com.akexorcist.kotlin.multiplatform.dayandnight

import androidx.compose.ui.graphics.Paint
import org.jetbrains.skia.BlendMode
import org.jetbrains.skia.FilterBlurMode
import org.jetbrains.skia.MaskFilter

actual fun Paint.applyBlurNativePaint(blurRadius: Float) {
    this.asFrameworkPaint().apply {
        blendMode = BlendMode.DST_OUT
        if (blurRadius > 0) {
            maskFilter = MaskFilter.makeBlur(FilterBlurMode.NORMAL, blurRadius / 2f, true)
        }
    }
}
