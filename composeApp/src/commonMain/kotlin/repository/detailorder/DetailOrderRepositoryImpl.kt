package repository.detailorder

import domain.RealmDetailOrder
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import org.mongodb.kbson.ObjectId

class DetailOrderRepositoryImpl : DetailOrderRepository {
    private val config: RealmConfiguration =
        RealmConfiguration.create(schema = setOf(RealmDetailOrder::class))
    private val realm: Realm = Realm.open(config)

    override fun update(
        titleId: ObjectId,
        list: List<ObjectId>,
    ) {
        val detailOrder = getItem(titleId)
        val realmList = list.toRealmList()

        realm.writeBlocking {
            if (detailOrder == null) {
                // データがなかったので新規作成
                val itemOrder = RealmDetailOrder().apply {
                    this.titleId = titleId
                    this.oderList = realmList
                }
                copyToRealm(itemOrder)
            } else {
                // データがあったのでアップデート
                findLatest(detailOrder)?.apply {
                    this.oderList = realmList
                }
            }
        }
    }

    override fun delete(titleId: ObjectId) {
        val order = realm
            .query<RealmDetailOrder>("titleId == $0", titleId)
            .first()
            .find()
            ?: return

        realm.writeBlocking {
            findLatest(order)?.let {
                delete(it)
            }
        }
    }

    override fun getItemOrder(
        titleId: ObjectId,
    ): List<ObjectId> {
        val list = getItem(titleId) ?: return emptyList()

        return list.oderList.toList()
    }

    private fun getItem(titleId: ObjectId): RealmDetailOrder? {
        return realm
            .query<RealmDetailOrder>(
                "titleId == $0",
                titleId,
            )
            .first()
            .find()
    }
}
