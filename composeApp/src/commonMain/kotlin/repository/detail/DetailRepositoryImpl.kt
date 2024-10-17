package repository.detail

import domain.Detail
import domain.RealmDetail
import domain.toDetail
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class DetailRepositoryImpl : DetailRepository {
    private val mutableRealmTitleFlow: MutableSharedFlow<List<Detail>> = MutableSharedFlow()
    private val titleFlow: SharedFlow<List<Detail>>
        get() = mutableRealmTitleFlow.asSharedFlow()

    private val config: RealmConfiguration =
        RealmConfiguration.create(schema = setOf(RealmDetail::class))
    private val realm: Realm = Realm.open(config)

    override val detailListState: StateFlow<List<Detail>>
        get() = titleFlow.stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(),
            initialValue = titleList,
        )

    private var titleList: List<Detail> = realm
        .query<RealmDetail>()
        .find()
        .toList()
        .map {
            it.toDetail()
        }
        set(value) {
            field = value
            CoroutineScope(Dispatchers.Default).launch {
                mutableRealmTitleFlow.emit(value)
            }
        }

    override fun updateAt(
        id: ObjectId,
        front: String,
        back: String,
        color: String,
    ) {
        realm.writeBlocking {
            val detail = realm.query<RealmDetail>("id == $0", id).first().find()
                ?: return@writeBlocking
            findLatest(detail)?.apply {
                this.front = front
                this.back = back
                this.color = color
            }
        }

        titleList = titleList.map { detail ->
            if (detail.id == id) {
                detail.copy(
                    front = front,
                    back = back,
                    color = color,
                )
            } else {
                detail
            }
        }.toMutableList()
    }

    override fun add() {
        realm.writeBlocking {
            val detail = RealmDetail()
            copyToRealm(detail)

            val list = titleList + detail.toDetail()
            titleList = list
        }
    }

    override fun deleteAt(id: ObjectId) {
        val detail = realm.query<RealmDetail>("id == $0", id).first().find()
            ?: return

        realm.writeBlocking {
            findLatest(detail)?.let {
                delete(it)
            }
        }

        titleList = titleList.filter {
            it.id != id
        }
    }
}
