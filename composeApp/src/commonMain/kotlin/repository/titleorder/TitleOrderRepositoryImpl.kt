package repository.titleorder

import domain.RealmTitleOrder
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import org.mongodb.kbson.ObjectId

class TitleOrderRepositoryImpl : TitleOrderRepository {
    private val config: RealmConfiguration =
        RealmConfiguration.create(schema = setOf(RealmTitleOrder::class))
    private val realm: Realm = Realm.open(config)

    override fun update(
        newList: List<ObjectId>,
    ) {
        val realmList = newList.toRealmList()

        val list = getItem()
        realm.writeBlocking {
            if (list == null) {
                // データがなかったので新規作成
                val itemOrder = RealmTitleOrder().apply {
                    this.oderList = realmList
                }
                copyToRealm(itemOrder)
            } else {
                // データがあったのでアップデート
                findLatest(list)?.apply {
                    this.oderList = realmList
                }
            }
        }
    }

    override fun getItemOrder(): List<ObjectId> {
        val list = getItem() ?: return emptyList()

        return list.oderList.toList()
    }

    private fun getItem(): RealmTitleOrder? {
        return realm
            .query<RealmTitleOrder>()
            .first()
            .find()
    }
}
