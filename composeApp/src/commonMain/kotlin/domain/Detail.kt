package domain

import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class RealmDetail : RealmObject {
    var id: ObjectId = ObjectId()
    var titleId: ObjectId = ObjectId()
    var front: String = ""
    var back: String = ""
    var color: String = ""
    var resultList: RealmList<Int> = emptyList<Int>().toRealmList()
}

data class Detail(
    val id: ObjectId,
    val titleId: ObjectId,
    val front: String,
    val back: String,
    val color: String,
    val resultList: List<Int>,
)

fun RealmDetail.toDetail() = Detail(
    id = id,
    titleId = titleId,
    front = front,
    back = back,
    color = color,
    resultList = resultList.toList(),
)
