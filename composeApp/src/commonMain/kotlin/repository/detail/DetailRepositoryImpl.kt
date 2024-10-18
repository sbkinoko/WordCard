package repository.detail

import domain.Detail
import domain.RealmDetail
import domain.toDetail
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
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
    private val mutableRealmTitleFlow: MutableSharedFlow<List<Detail>> = MutableSharedFlow(
        replay = 1,
    )
    private val titleFlow: SharedFlow<List<Detail>> = mutableRealmTitleFlow.asSharedFlow()

    private val config: RealmConfiguration =
        RealmConfiguration.create(schema = setOf(RealmDetail::class))
    private val realm: Realm = Realm.open(config)

    override val list: List<Detail>
        get() = detailList

    private var detailList: List<Detail> = emptyList()
        set(value) {
            field = value
            CoroutineScope(Dispatchers.Default).launch {
                mutableRealmTitleFlow.emit(value)
            }
        }

    override val detailListState: StateFlow<List<Detail>> = titleFlow.stateIn(
        scope = CoroutineScope(Dispatchers.Default),
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(),
        initialValue = detailList,
    )

    override var titleId: ObjectId? = null
        set(value) {
            field = value
            if (value == null) {
                detailList = emptyList()
                return
            }

            detailList = realm
                .query<RealmDetail>(
                    "titleId == $0", titleId
                )
                .find()
                .toList()
                .map {
                    it.toDetail()
                }
            println(detailList.size)
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

        detailList = detailList.map { detail ->
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

    override fun add(
        titleId: ObjectId,
    ) {
        realm.writeBlocking {
            val detail = RealmDetail().apply {
                this.titleId = titleId
            }
            copyToRealm(detail)

            val list = detailList + detail.toDetail()
            detailList = list
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

        detailList = detailList.filter {
            it.id != id
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
            val updatedList = list.takeLast(10)

            findLatest(detail)?.apply {
                resultList = updatedList.toRealmList()
            }

            detailList = detailList.map { detailItem ->
                if (detailItem.id == id) {
                    detailItem.copy(
                        resultList = list.takeLast(10)
                    )
                } else {
                    detailItem
                }
            }.toMutableList()
        }
    }
}
