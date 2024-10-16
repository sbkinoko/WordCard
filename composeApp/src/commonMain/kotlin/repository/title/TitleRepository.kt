package repository.title

import domain.Title
import kotlinx.coroutines.flow.StateFlow

interface TitleRepository {
    var titleList: List<Title>
    val titleState: StateFlow<List<Title>>

    fun add()

    fun updateAt(
        index: Int,
        title: String,
    )

    fun deleteAt(
        index: Int,
    )
}
