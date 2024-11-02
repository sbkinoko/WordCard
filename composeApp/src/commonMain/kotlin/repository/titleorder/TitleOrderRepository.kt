package repository.titleorder

import org.mongodb.kbson.ObjectId

interface TitleOrderRepository {
    fun update(
        newList: List<ObjectId>
    )

    fun getItemOrder(): List<ObjectId>
}
