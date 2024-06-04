package com.akexorcist.kotlin.multiplatform.dayandnight

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import org.jetbrains.compose.ui.tooling.preview.Preview

private val StartOffset = Offset(0f, 0f)
private val EndOffset = Offset(0f, Float.POSITIVE_INFINITY)

private object ContainerColors {
    val night = Brush.linearGradient(
        colorStops = arrayOf(
            0.05f to Color(0xFF000000),
            0.81f to Color(0xFF100B22),
            1f to Color(0xFF3A1401),
        ),
        start = StartOffset,
        end = EndOffset,
    )
    val noon = Brush.linearGradient(
        colorStops = arrayOf(
            0.4f to Color(0xFF9CCFFF),
            1f to Color(0xFFB8FFE5),
        ),
        start = StartOffset,
        end = EndOffset,
    )
}

@Composable
fun DayAndNightContainer(
    modifier: Modifier = Modifier,
    selected: Boolean,
    content: @Composable () -> Unit,
) {
    val noonContainerAlpha by animatedNoonContainerAlpha(selected = selected)
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = ContainerColors.night)
        )
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .alpha(noonContainerAlpha)
                .background(brush = ContainerColors.noon)
        )
        content()
    }
}

@Composable
private fun animatedNoonContainerAlpha(selected: Boolean): State<Float> =
    animateFloatAsState(
        when (selected) {
            true -> 1f
            false -> 0f
        },
        animationSpec = DayAndNightFloatAnimationSpec,
        label = "container_alpha",
    )


@Preview
@Composable
private fun DayAndNightContainerPreview() {
    val interactionSource = remember { MutableInteractionSource() }
    var seleced by remember { mutableStateOf(true) }
    
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = { seleced = !seleced },
                    enabled = true,
                    role = Role.Button,
                ),
            contentAlignment = Alignment.Center,
        ) {
            DayAndNightContainer(
                modifier = Modifier,
                selected = seleced,
                content = {},
            )
        }
    }
}