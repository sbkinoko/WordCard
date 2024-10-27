package domain

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import org.mongodb.kbson.ObjectId

class RealmDetailOrder : RealmObject {
    var titleId: ObjectId = ObjectId()
    var oderList: RealmList<ObjectId> = realmListOf()
}
