package com.nomanr.sample.ui.sample.samples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nomanr.lumo.ui.components.KLineChart
import com.nomanr.lumo.ui.components.KLineData
import com.nomanr.lumo.ui.components.TimeDimension
import com.nomanr.sample.ui.AppTheme
import com.nomanr.sample.ui.components.Text

@Composable
fun KLineChartSample() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        KLineChartShowcase()

        Spacer(modifier = Modifier.height(24.dp))

        KLineChartExamples()
    }
}

@Composable
private fun KLineChartShowcase() {
    Text(text = "K Line Chart", style = AppTheme.typography.h4)

    Column(
        modifier = Modifier.padding(16.dp),
    ) {
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
            KLineData(10L, 165f, 185f, 155f, 150f),
            KLineData(11L, 150f, 170f, 140f, 160f),
            KLineData(12L, 160f, 180f, 150f, 175f),
            KLineData(13L, 175f, 195f, 165f, 185f),
            KLineData(14L, 185f, 205f, 175f, 195f),
            KLineData(15L, 195f, 215f, 185f, 200f)
        )

        KLineChart(
            modifier = Modifier,
            data = sampleData,
            timeDimension = TimeDimension.DAILY,
            height = 300.dp
        )
    }
}

@Composable
private fun KLineChartExamples() {
    Column() {
        // Future examples can be added here
    }
}