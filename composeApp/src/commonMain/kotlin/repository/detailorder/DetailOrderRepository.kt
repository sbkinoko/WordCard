package repository.detailorder

import org.mongodb.kbson.ObjectId

interface DetailOrderRepository {
    fun update(
        titleId: ObjectId,
        list: List<ObjectId>
    )

    fun delete(
        titleId: ObjectId,
    )

    fun getItemOrder(titleId: ObjectId): List<ObjectId>
}
