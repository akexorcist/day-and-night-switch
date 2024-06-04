package com.akexorcist.kotlin.multiplatform.dayandnight

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

@Suppress("MemberVisibilityCanBePrivate")
class FourStarShape(
    val curveRatio: Float = 0.85f,
) : Shape {
    override fun createOutline(size: Size, layoutDirection: LayoutDirection, density: Density): Outline {
        val width = size.width
        val height = size.height
        val centerX = size.width / 2f
        val centerY = size.height / 2f
        val path = Path().apply {
            moveTo(x = centerX, y = 0f)
            quadraticBezierTo(
                x1 = centerX + (centerX * (1 - curveRatio)), y1 = centerY * curveRatio,
                x2 = width, y2 = centerY,
            )
            quadraticBezierTo(
                x1 = centerX + (centerX * (1 - curveRatio)), y1 = centerY + (centerY * (1 - curveRatio)),
                x2 = centerX, y2 = height,
            )
            quadraticBezierTo(
                x1 = centerX * curveRatio, y1 = centerY + (centerY * (1 - curveRatio)),
                x2 = 0f, y2 = centerY,
            )
            quadraticBezierTo(
                x1 = centerX * curveRatio, y1 = centerY * curveRatio,
                x2 = centerX, y2 = 0f,
            )
            close()
        }
        return Outline.Generic(path)
    }
}
