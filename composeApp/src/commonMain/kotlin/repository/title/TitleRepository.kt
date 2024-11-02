package repository.title

import domain.Title
import org.mongodb.kbson.ObjectId

interface TitleRepository {

    fun add(
        objectId: ObjectId,
    )

    fun updateAt(
        id: ObjectId,
        title: String,
    )

    fun delete(
        id: ObjectId,
    )

    fun get(
        id: ObjectId,
    ): Title?
}
