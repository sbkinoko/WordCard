package domain

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class RealmTitle : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var title: String = ""
}

data class Title(
    val id: ObjectId,
    val title: String,
)

fun RealmTitle.toTitle() = Title(
    id = id,
    title = title,
)
