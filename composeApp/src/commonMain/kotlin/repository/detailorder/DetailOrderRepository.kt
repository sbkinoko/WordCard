package repository.detailorder

import kotlinx.coroutines.flow.StateFlow
import org.mongodb.kbson.ObjectId

interface DetailOrderRepository {
    val detailOrderState: StateFlow<List<ObjectId>>
    val isEmpty: Boolean

    var titleId: ObjectId?
    var isLoading: Boolean

    val list: List<ObjectId>

    fun update(
        id: ObjectId,
        list: List<ObjectId>
    )

    fun delete(
        titleId: ObjectId,
    )
}
