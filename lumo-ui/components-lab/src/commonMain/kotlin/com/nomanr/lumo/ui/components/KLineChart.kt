package com.nomanr.lumo.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nomanr.lumo.ui.AppTheme
import com.nomanr.lumo.ui.Red500
import com.nomanr.lumo.ui.Green500
import com.nomanr.lumo.ui.Gray300
import com.nomanr.lumo.ui.Gray600

@Composable
fun KLineChart(
    modifier: Modifier = Modifier,
    data: List<KLineData>,
    timeDimension: TimeDimension = TimeDimension.DAILY,
    height: Dp = 300.dp,
) {
    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    
    val maxPrice = remember(data) { data.maxOfOrNull { maxOf(it.high, it.low) } ?: 0f }
    val minPrice = remember(data) { data.minOfOrNull { minOf(it.high, it.low) } ?: 0f }
    val priceRange = maxPrice - minPrice
    
    Canvas(
        modifier = modifier
            .size(height = height)
            .pointerInput(Unit) {
                detectTransformGestures {_, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(1f, 5f)
                    offsetX += pan.x
                }
            }
    ) {
        val chartWidth = size.width
        val chartHeight = size.height
        val padding = 20f
        val usableWidth = chartWidth - padding * 2
        val usableHeight = chartHeight - padding * 2
        
        // Draw grid
        drawGrid(padding, usableWidth, usableHeight)
        
        // Draw K lines
        if (data.isNotEmpty() && priceRange > 0) {
            val klineWidth = (usableWidth / (data.size * scale)).coerceAtMost(10f).coerceAtLeast(2f)
            val spacing = klineWidth * 1.5f
            
            data.forEachIndexed { index, klineData ->
                val x = padding + index * spacing - offsetX
                if (x + klineWidth > 0 && x < usableWidth + padding) {
                    val color = if (klineData.close > klineData.open) Red500 else Green500
                    
                    // Calculate price positions
                    val openY = padding + usableHeight - ((klineData.open - minPrice) / priceRange) * usableHeight
                    val closeY = padding + usableHeight - ((klineData.close - minPrice) / priceRange) * usableHeight
                    val highY = padding + usableHeight - ((klineData.high - minPrice) / priceRange) * usableHeight
                    val lowY = padding + usableHeight - ((klineData.low - minPrice) / priceRange) * usableHeight
                    
                    // Draw wick
                    drawLine(
                        color = color,
                        start = Offset(x + klineWidth / 2, highY),
                        end = Offset(x + klineWidth / 2, lowY),
                        strokeWidth = 1.dp.toPx()
                    )
                    
                    // Draw body
                    val bodyTop = minOf(openY, closeY)
                    val bodyHeight = maxOf(openY, closeY) - bodyTop
                    
                    drawRect(
                        color = color,
                        topLeft = Offset(x, bodyTop),
                        size = Size(klineWidth, if (bodyHeight < 1.dp.toPx()) 1.dp.toPx() else bodyHeight)
                    )
                }
            }
        }
    }
}

private fun DrawScope.drawGrid(padding: Float, usableWidth: Float, usableHeight: Float) {
    // Draw horizontal grid lines
    for (i in 0..5) {
        val y = padding + (usableHeight / 5) * i
        drawLine(
            color = Gray300,
            start = Offset(padding, y),
            end = Offset(padding + usableWidth, y),
            strokeWidth = 0.5.dp.toPx()
        )
    }
    
    // Draw vertical grid lines
    for (i in 0..6) {
        val x = padding + (usableWidth / 6) * i
        drawLine(
            color = Gray300,
            start = Offset(x, padding),
            end = Offset(x, padding + usableHeight),
            strokeWidth = 0.5.dp.toPx()
        )
    }
    
    // Draw border
    drawRect(
        color = Gray600,
        topLeft = Offset(padding, padding),
        size = Size(usableWidth, usableHeight),
        style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.dp.toPx())
    )
}

data class KLineData(
    val timestamp: Long,
    val open: Float,
    val high: Float,
    val low: Float,
    val close: Float
)

enum class TimeDimension {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}

@Composable
fun KLineChartPreview() {
    // Sample data
    val sampleData = listOf(
        KLineData(1L, 100f, 120f, 90f, 110f),
        KLineData(2L, 110f, 130f, 100f, 120f),
        KLineData(3L, 120f, 140f, 110f, 105f),
        KLineData(4L, 105f, 125f, 95f, 115f),
        KLineData(5L, 115f, 135f, 105f, 130f),
        KLineData(6L, 130f, 150f, 120f, 125f),
        KLineData(7L, 125f, 145f, 115f, 140f),
        KLineData(8L, 140f, 160f, 130f, 155f),
        KLineData(9L, 155f, 175f, 145f, 165f),
        KLineData(10L, 165f, 185f, 155f, 150f)
    )
    
    AppTheme {
        KLineChart(
            modifier = Modifier.fillMaxSize(),
            data = sampleData,
            timeDimension = TimeDimension.DAILY
        )
    }
}