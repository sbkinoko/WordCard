package domain

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class RealmTitleOrder : RealmObject {
    @PrimaryKey
    var id:ObjectId = ObjectId()
    var oderList: RealmList<ObjectId> = realmListOf()
}
