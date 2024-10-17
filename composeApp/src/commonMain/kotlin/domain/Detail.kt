package domain

var _id = 0

data class Detail(
    val id: Int = _id,
    val front: String = "",
    val back: String = "",
    val color: String = "",
) {
    init {
        _id++
    }
}
