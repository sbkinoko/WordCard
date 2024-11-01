package view.detail.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun SearchButton(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier
                .weight(1f),
            value = "",
            onValueChange = {},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
        )

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {},
        ) {
            Text(text = "JUMP")
        }
    }
}
