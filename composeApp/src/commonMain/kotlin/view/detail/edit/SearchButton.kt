package view.detail.edit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SearchButton(
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val searchText = remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier
                .weight(1f),
            value = searchText.value,
            onValueChange = {
                searchText.value = it
            },
        )

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onClick(searchText.value)
            },
        ) {
            Text(text = "Filter")
        }
    }
}
