package view.dialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import common.Const.Color.Companion.deleteButtonColor

@Composable
fun DeleteDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = "削除確認ダイアログ") },
        text = { Text(text = "元には戻せません\nいいですか？") },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = deleteButtonColor
                )
            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissRequest
            ) {
                Text(text = "Cancel")
            }
        }
    )
}
