package repository.title

import domain.Title
import kotlinx.coroutines.flow.StateFlow
import org.mongodb.kbson.ObjectId

interface TitleRepository {
    val titleState: StateFlow<List<Title>>

    fun add()

    fun updateAt(
        index: Int,
        title: String,
    )

    fun delete(
        id:ObjectId,
    )
}
