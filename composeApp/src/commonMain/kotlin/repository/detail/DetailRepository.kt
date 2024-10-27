package repository.detail

import domain.Detail
import kotlinx.coroutines.flow.StateFlow
import org.mongodb.kbson.ObjectId

interface DetailRepository {
    val detailListState: StateFlow<List<Detail>>
    val list: List<Detail>

    var titleId: ObjectId?
    var isLoading: Boolean

    fun getDetail(objectId: ObjectId): Detail?

    fun add(
        titleId: ObjectId,
    )

    fun updateAt(
        id: ObjectId,
        front: String,
        back: String,
        color: String,
    )

    fun deleteAt(
        id: ObjectId,
    )

    fun updateResult(
        id: ObjectId,
        result: Boolean,
    )
}
