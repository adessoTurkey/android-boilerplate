package com.adesso.movee.scene.showcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class ShowCaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainUi()
        }
    }
}

@Composable
fun MainUi() {
    Box {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(16.dp, 16.dp, 16.dp, 16.dp)
        ) {
            Text(text = "Compose")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainUi()
}
