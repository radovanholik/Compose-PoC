package com.example.compose_poc.ui.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.compose_poc.ui.screen.feature_list.FeatureType

@Composable
fun FeatureCard(
    type: FeatureType,
    onClick: () -> Unit
) {
    Card (
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable(onClick = onClick),
        elevation = 8.dp
    ) {
        Text(
            text = stringResource(id = type.title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            style = MaterialTheme.typography.h5

        )
    }
}