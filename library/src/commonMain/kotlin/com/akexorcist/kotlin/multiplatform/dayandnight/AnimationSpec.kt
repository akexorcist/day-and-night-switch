package com.akexorcist.kotlin.multiplatform.dayandnight

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

internal const val DayAndNightAnimationDuration = 700

internal val DayAndNightColorAnimationSpec: AnimationSpec<Color> = tween(
    durationMillis = DayAndNightAnimationDuration,
)
internal val DayAndNightFloatAnimationSpec: AnimationSpec<Float> = tween(
    durationMillis = DayAndNightAnimationDuration,
)
internal val DayAndNightDpAnimationSpec: AnimationSpec<Dp> = tween(
    durationMillis = DayAndNightAnimationDuration,
)
