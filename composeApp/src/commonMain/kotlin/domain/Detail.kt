package domain

import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class RealmDetail : RealmObject {
    var id: ObjectId = ObjectId()
    var titleId: ObjectId = ObjectId()
    var front: String = ""
    var back: String = ""
    var color: String = ""
}

data class Detail(
    val id: ObjectId,
    val titleId: ObjectId,
    val front: String,
    val back: String,
    val color: String,
)

fun RealmDetail.toDetail() = Detail(
    id = id,
    titleId = titleId,
    front = front,
    back = back,
    color = color,
)
