package com.akexorcist.kotlin.multiplatform.dayandnight

import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInQuad
import androidx.compose.animation.core.EaseInSine
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

private object ThumbColors {
    val sun = Color(0xFFEFCE55)
    val topInnerSun = Color(0xFFF2EBCF)
    val bottomInnerSun = Color(0xFFBFA965)

    val moon = Color(0xFFCDCFD5)
    val topInnerMoon = Color(0xFFFBFBFB)
    val bottomInnerMoon = Color(0xFFACADAF)

    val moonCrate = Color(0xFFA2A6B8)
    val innerMoonCrate = Color(0xFF8C8D9E)
    val outerMoonCrate = Color(0xFFD7D8DD)

    val thumbShadow = Color(0x0FFFFFFF)
}

private val ThumbSize = 64.dp
private val FirstThumbShadowSize = 125.dp
private val SecondThumbShadowSize = 192.dp
private val ThirdThumbShadowSize = 264.dp
private val HoveredThumbSize = 10.dp
private val ThumbHorizontalPadding = 10.dp

@Composable
internal fun SunAndMoonThumbContainer(
    selected: Boolean,
    hovered: Boolean,
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .clip(RoundedCornerShape(100)),
        contentAlignment = Alignment.CenterStart,
    ) {
        val thumbOffsetX by animatedThumbOffsetX(
            selected = selected,
            containerWidth = maxWidth,
            horizontalPadding = ThumbHorizontalPadding,
            size = ThumbSize,
        )
        SunAndMoonWithLightEffect(
            offsetX = thumbOffsetX,
            selected = selected,
            hovered = hovered,
            size = ThumbSize,
        )
    }
}

@Composable
private fun SunAndMoonWithLightEffect(
    offsetX: Dp,
    selected: Boolean,
    hovered: Boolean,
    size: Dp,
) {
    val firstShadowScale by animatedThumbShadowSize(
        from = ThumbSize,
        to = FirstThumbShadowSize,
        extra = HoveredThumbSize,
        hovered = hovered,
    )
    val secondShadowScale by animatedThumbShadowSize(
        from = ThumbSize,
        to = SecondThumbShadowSize,
        extra = HoveredThumbSize,
        hovered = hovered,
    )
    val thirdShadowScale by animatedThumbShadowSize(
        from = ThumbSize,
        to = ThirdThumbShadowSize,
        extra = HoveredThumbSize,
        hovered = hovered,
    )
    Box(
        modifier = Modifier.offset(x = offsetX),
    ) {
        ThumbShadow(
            size = size,
            scale = thirdShadowScale,
        )
        ThumbShadow(
            size = size,
            scale = secondShadowScale,
        )
        ThumbShadow(
            size = size,
            scale = firstShadowScale,
        )
        SunAndMoonThumb(
            selected = selected,
            size = size,
        )
    }
}

@Composable
private fun SunAndMoonThumb(
    modifier: Modifier = Modifier,
    selected: Boolean,
    size: Dp,
) {
    val moonThumbOffsetX by animatedMoonThumbOffsetX(
        selected = selected,
        size = size,
    )
    Box(
        modifier = modifier
            .shadow(
                6.dp,
                shape = CircleShape,
            )
            .size(size)
    ) {
        SunThumb(size = size)
        Box(
            modifier = Modifier
                .offset(x = moonThumbOffsetX)
                .fillMaxSize()
                .clip(CircleShape),
        ) {
            MoonThumb(size = size)
        }
    }
}

@Composable
private fun ThumbShadow(
    scale: Float,
    size: Dp,
) {
    Spacer(
        modifier = Modifier
            .scale(scale)
            .size(size)
            .background(
                color = ThumbColors.thumbShadow,
                shape = CircleShape,
            )
    )
}

@Composable
private fun SunThumb(
    size: Dp,
) {
    Spacer(
        modifier = Modifier
            .size(size)
            .background(
                color = ThumbColors.sun,
                shape = CircleShape,
            )
            .innerShadow(
                blur = 4.dp,
                color = ThumbColors.bottomInnerSun,
                cornersRadius = size / 2f,
                offsetX = (-2).dp,
                offsetY = (-4).dp
            )
            .innerShadow(
                blur = 6.dp,
                color = ThumbColors.topInnerSun,
                cornersRadius = size / 2f,
                offsetX = 3.dp,
                offsetY = 4.dp
            ),
    )
}

@Composable
private fun MoonThumb(
    size: Dp,
) {
    Box(modifier = Modifier.size(size)) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = ThumbColors.moon,
                    shape = CircleShape,
                )
                .innerShadow(
                    blur = 4.dp,
                    color = ThumbColors.bottomInnerMoon,
                    cornersRadius = size / 2f,
                    offsetX = (-2).dp,
                    offsetY = (-4).dp
                )
                .innerShadow(
                    blur = 6.dp,
                    color = ThumbColors.topInnerMoon,
                    cornersRadius = size / 2f,
                    offsetX = 3.dp,
                    offsetY = 4.dp
                ),
        )
        MoonCrate(
            modifier = Modifier.offset(
                x = size * 0.14f,
                y = size * 0.42f,
            ),
            crateSize = 26.dp,
        )
        MoonCrate(
            modifier = Modifier.offset(
                x = size * 0.37f,
                y = size * 0.10f,
            ),
            crateSize = 14.dp,
        )
        MoonCrate(
            modifier = Modifier.offset(
                x = size * 0.63f,
                y = size * 0.58f,
            ),
            crateSize = 16.dp,
        )
    }
}

@Composable
private fun MoonCrate(
    modifier: Modifier,
    crateSize: Dp,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Spacer(
            modifier = Modifier
                .size(crateSize)
                .blur(0.25.dp)
                .padding(0.5.dp)
                .background(
                    color = ThumbColors.outerMoonCrate,
                    shape = CircleShape,
                ),
        )
        Spacer(
            modifier = Modifier
                .size(crateSize)
                .padding(1.dp)
                .background(
                    color = ThumbColors.moonCrate,
                    shape = CircleShape,
                )
                .innerShadow(
                    blur = 6.dp,
                    color = ThumbColors.innerMoonCrate,
                    cornersRadius = crateSize / 2f,
                    offsetX = 3.dp,
                    offsetY = 4.dp
                ),
        )
    }
}

@Composable
private fun animatedThumbShadowSize(
    from: Dp,
    to: Dp,
    extra: Dp,
    hovered: Boolean,
) =
    animateFloatAsState(
        targetValue = when (hovered) {
            true -> (to + extra) / from
            false -> to / from
        },
        animationSpec = DayAndNightFloatAnimationSpec,
        label = "thumb_shadow_size"
    )

@Composable
private fun animatedThumbOffsetX(
    selected: Boolean,
    containerWidth: Dp,
    horizontalPadding: Dp,
    size: Dp,
) =
    animateDpAsState(
        when (selected) {
            true -> horizontalPadding
            false -> containerWidth - (size + horizontalPadding)
        },
        animationSpec = tween(
            durationMillis = DayAndNightAnimationDuration,
            easing = Ease,
        ),
        label = "thumb_offset_x",
    )

@Composable
private fun animatedMoonThumbOffsetX(
    selected: Boolean,
    size: Dp,
) =
    animateDpAsState(
        when (selected) {
            true -> size
            false -> 0.dp
        },
        animationSpec = tween(
            durationMillis = DayAndNightAnimationDuration,
            easing = Ease,
        ),
        label = "moon_thumb_offset_x",
    )
@Preview
@Composable
private fun SunThumbPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            SunThumb(size = 64.dp)
        }
    }
}

@Preview
@Composable
private fun MoonThumbPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            MoonThumb(size = 64.dp)
        }
    }
}

@Preview
@Composable
private fun SunAndMoonWithLightEffectPreview() {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()
    var seleced by remember { mutableStateOf(true) }

    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { seleced = !seleced },
                    enabled = true,
                    role = Role.Button,
                ),
            ) {
                SunAndMoonWithLightEffect(
                    offsetX = 0.dp,
                    selected = seleced,
                    hovered = hovered,
                    size = 64.dp,
                )
            }
        }
    }
}
