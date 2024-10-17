package repository.detail

import domain.Detail
import kotlinx.coroutines.flow.StateFlow
import org.mongodb.kbson.ObjectId

interface DetailRepository {
    val detailListState: StateFlow<List<Detail>>

    fun add()

    fun updateAt(
        id: ObjectId,
        front: String,
        back: String,
        color: String,
    )

    fun deleteAt(
        id: ObjectId,
    )
}
