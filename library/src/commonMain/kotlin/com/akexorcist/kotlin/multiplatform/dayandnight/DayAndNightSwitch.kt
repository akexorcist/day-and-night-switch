package com.akexorcist.kotlin.multiplatform.dayandnight

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

private object ContentColors {
    val noonSwitchBackground = Color(0xFF509BE3)
    val nightSwitchBackground = Color(0xFF090714)

    val noonInnerShadow = Color(0xAA000000)
    val noonTopOuterBorder = Color(0xAA000000)
    val noonBottomOuterBorder = Color.White

    val foregroundCloud = Color.White
    val backgroundCloud = Color(0xFFABCDF2)

    val star = Color.White
}

private val SwitchWidth = 230.dp
private val SwitchHeight = 85.dp

private typealias BackgroundDecoration = Triple<Dp, Dp, Dp>

@Composable
fun DayAndNightSwitch(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onSwitchToggle: (Boolean) -> Unit,
    thumb: @Composable (Boolean, Boolean) -> Unit = { _, hovered ->
        SunAndMoonThumbContainer(
            selected = selected,
            hovered = hovered,
        )
    },
) {
    val interactionSource = remember { MutableInteractionSource() }
    val hovered by interactionSource.collectIsHoveredAsState()

    Box(
        modifier = modifier
            .width(SwitchWidth)
            .height(SwitchHeight)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onSwitchToggle(!selected) },
                enabled = true,
                role = Role.Button,
            )
    ) {
        OuterBorder(selected = selected)
        SwitchBackground(selected = selected)
        thumb(selected, hovered)
        InnerShadow()
    }
}

@Composable
private fun SwitchBackground(
    selected: Boolean,
) {
    val switchBackgroundColor by animatedSwitchBackgroundColor(selected = selected)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .background(
                color = switchBackgroundColor,
                shape = RoundedCornerShape(100),
            )
            .clip(shape = RoundedCornerShape(100))
    ) {
        CloudBackground(selected = selected)
        StarBackground(selected = selected)
    }
}

@Composable
private fun OuterBorder(
    selected: Boolean,
) {
    val bottomOuterBorderAlpha by animatedBottomOuterBorderAlpha(selected = selected)
    val topOuterBorderAlpha by animatedTopOuterBorderAlpha(selected = selected)
    BottomOuterBorder(alpha = bottomOuterBorderAlpha)
    TopOuterBorder(alpha = topOuterBorderAlpha)
}

@Composable
private fun InnerShadow() {
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .innerShadow(
                blur = 6.dp,
                color = ContentColors.noonInnerShadow,
                cornersRadius = SwitchHeight / 2f,
                offsetX = 0.dp,
                offsetY = 6.dp
            ),
    )
}

@Composable
private fun StarBackground(
    selected: Boolean,
) {
    val starAlpha by animatedStarAlpha(selected = selected)
    val foregroundStarOffsetY by animatedForegroundStarOffsetY(selected = selected)
    val backgroundStarOffsetY by animatedBackgroundStarOffsetY(selected = selected)
    BackgroundStar(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = backgroundStarOffsetY)
            .alpha(starAlpha),
    )
    ForegroundStar(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = foregroundStarOffsetY)
            .alpha(starAlpha),
    )
}

@Composable
fun ForegroundStar(
    modifier: Modifier = Modifier,
) {
    val clouds = remember {
        listOf(
            BackgroundDecoration(14.dp, 33.dp, 12.dp),
            BackgroundDecoration(11.dp, 99.dp, 51.dp),
            BackgroundDecoration(15.dp, 132.dp, 11.dp),
        )
    }

    Box(modifier = modifier) {
        clouds.forEach { (size, offsetX, offsetY) ->
            Star(
                size = size,
                offsetX = offsetX,
                offsetY = offsetY,
            )
        }
    }
}

@Composable
fun BackgroundStar(
    modifier: Modifier = Modifier,
) {
    val clouds = remember {
        listOf(
            BackgroundDecoration(9.dp, 17.dp, 21.dp),
            BackgroundDecoration(7.dp, 20.dp, 64.dp),
            BackgroundDecoration(5.dp, 31.dp, 52.dp),
            BackgroundDecoration(10.dp, 38.dp, 60.dp),
            BackgroundDecoration(10.dp, 52.dp, 35.dp),
            BackgroundDecoration(6.dp, 90.dp, 43.dp),
            BackgroundDecoration(5.dp, 102.dp, 18.dp),
            BackgroundDecoration(8.dp, 121.dp, 51.dp),
        )
    }

    Box(modifier = modifier) {
        clouds.forEach { (size, offsetX, offsetY) ->
            Star(
                size = size,
                offsetX = offsetX,
                offsetY = offsetY,
            )
        }
    }
}

@Composable
private fun Star(
    size: Dp,
    offsetX: Dp,
    offsetY: Dp,
) {
    Spacer(
        modifier = Modifier
            .size(size)
            .offset(
                x = offsetX,
                y = offsetY,
            )
            .background(
                color = ContentColors.star,
                shape = FourStarShape(
                ),
            )
    )
}

@Composable
private fun CloudBackground(
    selected: Boolean,
) {
    val cloudAlpha by animatedCloudAlpha(selected = selected)
    val foregroundCloudOffsetY by animatedForegroundCloudOffsetY(selected = selected)
    val backgroundCloudOffsetY by animatedBackgroundCloudOffsetY(selected = selected)
    BackgroundCloud(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = backgroundCloudOffsetY)
            .alpha(cloudAlpha),
    )
    ForegroundCloud(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = foregroundCloudOffsetY)
            .alpha(cloudAlpha),
    )
}

@Composable
fun ForegroundCloud(
    modifier: Modifier = Modifier,
) {
    val clouds = remember {
        listOf(
            BackgroundDecoration(58.dp, 2.dp, 69.dp),
            BackgroundDecoration(56.dp, 32.dp, 66.dp),
            BackgroundDecoration(53.dp, 72.dp, 63.dp),
            BackgroundDecoration(44.dp, 110.dp, 69.dp),
            BackgroundDecoration(51.dp, 137.dp, 62.dp),
            BackgroundDecoration(47.dp, 171.dp, 43.dp),
            BackgroundDecoration(66.dp, 191.dp, 10.dp),
            BackgroundDecoration(52.dp, 206.dp, 55.dp),
        )
    }
    Box(modifier = modifier) {
        clouds.forEach { (size, offsetX, offsetY) ->
            Cloud(
                color = ContentColors.foregroundCloud,
                size = size,
                offsetX = offsetX,
                offsetY = offsetY,
            )
        }
    }
}

@Composable
fun BackgroundCloud(
    modifier: Modifier = Modifier,
) {
    val clouds = remember {
        listOf(
            BackgroundDecoration(63.dp, 1.dp, 55.dp),
            BackgroundDecoration(63.dp, 28.dp, 43.dp),
            BackgroundDecoration(63.dp, 63.dp, 40.dp),
            BackgroundDecoration(49.dp, 107.dp, 50.dp),
            BackgroundDecoration(63.dp, 127.dp, 34.dp),
            BackgroundDecoration(58.dp, 161.dp, 25.dp),
            BackgroundDecoration(63.dp, 178.dp, 1.dp),
        )
    }
    Box(modifier = modifier) {
        clouds.forEach { (size, offsetX, offsetY) ->
            Cloud(
                color = ContentColors.backgroundCloud,
                size = size,
                offsetX = offsetX,
                offsetY = offsetY,
            )
        }
    }
}

@Composable
private fun Cloud(
    color: Color,
    size: Dp,
    offsetX: Dp,
    offsetY: Dp,
) {
    Spacer(
        modifier = Modifier
            .size(size)
            .offset(
                x = offsetX,
                y = offsetY,
            )
            .background(
                color = color,
                shape = CircleShape,
            )
    )
}

@Composable
private fun TopOuterBorder(
    alpha: Float,
) {
    Spacer(
        Modifier
            .fillMaxSize()
            .alpha(alpha)
            .blur(1.dp)
            .padding(
                top = 1.dp,
                start = 2.dp,
                end = 3.dp,
                bottom = 4.dp,
            )
            .background(
                color = ContentColors.noonTopOuterBorder,
                shape = RoundedCornerShape(100),
            )
    )
}

@Composable
private fun BottomOuterBorder(
    alpha: Float,
) {
    Spacer(
        Modifier
            .fillMaxSize()
            .alpha(alpha)
            .blur(1.dp)
            .padding(
                top = 4.dp,
                start = 3.dp,
                end = 2.dp,
                bottom = 1.dp,
            )
            .background(
                color = ContentColors.noonBottomOuterBorder,
                shape = RoundedCornerShape(100),
            )
    )
}

@Composable
private fun animatedSwitchBackgroundColor(selected: Boolean) =
    animateColorAsState(
        when (selected) {
            true -> ContentColors.noonSwitchBackground
            false -> ContentColors.nightSwitchBackground
        },
        animationSpec = DayAndNightColorAnimationSpec,
        label = "switch_background",
    )

@Composable
private fun animatedTopOuterBorderAlpha(selected: Boolean) =
    animateFloatAsState(
        when (selected) {
            true -> 1f
            false -> 0f
        },
        animationSpec = DayAndNightFloatAnimationSpec,
        label = "output_border_alpha",
    )

@Composable
private fun animatedBottomOuterBorderAlpha(selected: Boolean) =
    animateFloatAsState(
        when (selected) {
            true -> 1f
            false -> 0.15f
        },
        animationSpec = DayAndNightFloatAnimationSpec,
        label = "output_border_alpha",
    )

@Composable
private fun animatedCloudAlpha(selected: Boolean) =
    animateFloatAsState(
        when (selected) {
            true -> 1f
            false -> 0.5f
        },
        animationSpec = DayAndNightFloatAnimationSpec,
        label = "cloud_alpha",
    )

@Composable
private fun animatedForegroundCloudOffsetY(selected: Boolean) =
    animateDpAsState(
        when (selected) {
            true -> 0.dp
            false -> SwitchHeight * 1.15f
        },
        animationSpec = DayAndNightDpAnimationSpec,
        label = "foreground_cloud_offset_y",
    )

@Composable
private fun animatedBackgroundCloudOffsetY(selected: Boolean) =
    animateDpAsState(
        when (selected) {
            true -> 0.dp
            false -> SwitchHeight
        },
        animationSpec = DayAndNightDpAnimationSpec,
        label = "background_cloud_offset_y",
    )

@Composable
private fun animatedStarAlpha(selected: Boolean) =
    animateFloatAsState(
        when (selected) {
            true -> 0.5f
            false -> 1f
        },
        animationSpec = DayAndNightFloatAnimationSpec,
        label = "star_alpha",
    )

@Composable
private fun animatedForegroundStarOffsetY(selected: Boolean) =
    animateDpAsState(
        when (selected) {
            true -> -SwitchHeight
            false -> 0.dp
        },
        animationSpec = DayAndNightDpAnimationSpec,
        label = "foreground_star_offset_y",
    )

@Composable
private fun animatedBackgroundStarOffsetY(selected: Boolean) =
    animateDpAsState(
        when (selected) {
            true -> -SwitchHeight * 2f
            false -> 0.dp
        },
        animationSpec = DayAndNightDpAnimationSpec,
        label = "background_star_offset_y",
    )

@Preview
@Composable
fun DayAndNightSwitchPreview() {
    var selected by remember { mutableStateOf(true) }
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .width(230.dp)
                    .height(85.dp),
            ) {
                DayAndNightSwitch(
                    modifier = Modifier,
                    selected = selected,
                    onSwitchToggle = { selected = !selected },
                    thumb = { _, _ -> },
                )
            }
        }
    }
}
