package repository.title


import domain.RealmTitle
import domain.Title
import domain.toTitle
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import org.mongodb.kbson.ObjectId

class TitleRepositoryImpl : TitleRepository {
    private val config: RealmConfiguration =
        RealmConfiguration.create(schema = setOf(RealmTitle::class))
    private val realm: Realm = Realm.open(config)

    override fun get(id: ObjectId): Title? {
        return realm.query<RealmTitle>("id == $0", id)
            .first()
            .find()
            ?.toTitle()
    }

    override fun updateAt(
        id: ObjectId,
        title: String
    ) {
        val target = realm.query<RealmTitle>("id == $0", id).first().find()
            ?: return
        realm.writeBlocking {
            findLatest(
                target
            )?.apply {
                this.title = title
            }
        }
    }

    override fun add(
        objectId: ObjectId,
    ) {
        realm.writeBlocking {
            val title = RealmTitle().apply {
                this.id = objectId
            }
            copyToRealm(
                title
            )
        }
    }

    override fun delete(id: ObjectId) {
        realm.writeBlocking {
            val ob = realm.query<RealmTitle>("id == $0", id).first().find()
            if (ob != null) {
                findLatest(ob)?.let {
                    delete(it)
                }
            }
        }
    }
}
