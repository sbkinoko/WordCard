package repository.detail

import domain.Constant
import domain.Detail
import domain.RealmDetail
import domain.toDetail
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import org.mongodb.kbson.ObjectId

class DetailRepositoryImpl : DetailRepository {
    private val config: RealmConfiguration =
        RealmConfiguration.create(schema = setOf(RealmDetail::class))
    private val realm: Realm = Realm.open(config)

    override fun getDetail(objectId: ObjectId): Detail? {
        val result = realm.query<RealmDetail>("id == $0", objectId)
            .find()

        if (result.isEmpty()) {
            return null
        }

        return result.first()
            .toDetail()
    }

    override fun updateAt(
        id: ObjectId,
        front: String,
        back: String,
        color: String,
    ) {
        realm.writeBlocking {
            val detail = realm
                .query<RealmDetail>("id == $0", id)
                .first()
                .find()
                ?: return@writeBlocking
            findLatest(detail)?.apply {
                this.front = front
                this.back = back
                this.color = color
            }
        }
    }

    override fun add(
        titleId: ObjectId,
        id: ObjectId,
    ) {
        realm.writeBlocking {
            val detail = RealmDetail().apply {
                this.titleId = titleId
                this.id = id
            }
            copyToRealm(detail)
        }
    }

    override fun deleteAt(id: ObjectId) {
        val detail = realm
            .query<RealmDetail>("id == $0", id)
            .first()
            .find()
            ?: return

        realm.writeBlocking {
            findLatest(detail)?.let {
                delete(it)
            }
        }
    }

    override fun updateResult(
        id: ObjectId,
        result: Boolean
    ) {
        realm.writeBlocking {
            val detail = realm
                .query<RealmDetail>("id == $0", id)
                .first()
                .find()
                ?: return@writeBlocking

            val list = detail.resultList + if (result) {
                1
            } else {
                0
            }
            val updatedList = list.takeLast(
                Constant.RESULT_LENGTH
            )

            findLatest(detail)?.apply {
                resultList = updatedList.toRealmList()
            }
        }
    }

    override fun getItems(
        titleId: ObjectId,
    ): List<Detail> {
        return realm
            .query<RealmDetail>(
                "titleId == $0", titleId
            )
            .find()
            .toList()
            .map {
                it.toDetail()
            }
    }
}
