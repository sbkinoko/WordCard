package repository.detail

import domain.Detail
import kotlinx.coroutines.flow.StateFlow
import org.mongodb.kbson.ObjectId

interface DetailRepository {
    fun getDetail(objectId: ObjectId): Detail?

    fun add(
        titleId: ObjectId,
        id: ObjectId,
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

    fun getItems(
        titleId: ObjectId,
    ): List<Detail>
}
