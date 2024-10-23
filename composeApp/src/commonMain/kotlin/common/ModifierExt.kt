package common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.commonBorder(
    color: Color = Color.Black
) = then(
    Modifier.border(
        width = 3.dp,
        color = color,
        shape = RoundedCornerShape(5.dp)
    )
)

fun Modifier.componentBackground() = then(
    Modifier.background(
        color = Const.Color.componentBackgroundColor,
        shape = RoundedCornerShape(5.dp)
    )
)
