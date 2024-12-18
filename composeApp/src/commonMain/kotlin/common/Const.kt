package common

import androidx.compose.ui.graphics.Color

class Const {
    class Color {
        companion object {
            private val gray = Color(0xFFE0E0E0)
            private val white = Color(0xFFFFFFFF)
            private val red = Color(0xFFFF0000)

            val backgroundColor = gray
            val componentBackgroundColor = white
            val deleteButtonColor = red
        }
    }
}
