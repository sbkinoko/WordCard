package repository.title

import domain.Title
import kotlinx.coroutines.flow.SharedFlow

interface TitleRepository {
    var titleList: List<Title>
    val titleFlow: SharedFlow<List<Title>>

    fun add()

    fun updateAt(
        index: Int,
        title: String,
    )

    fun deleteAt(
        index: Int,
    )
}
