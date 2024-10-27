package repository.detailorder

import domain.RealmDetailOrder
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.mongodb.kbson.ObjectId

class DetailOrderRepositoryImpl : DetailOrderRepository {
    private val mutableListFlow: MutableSharedFlow<List<ObjectId>> =
        MutableSharedFlow(
            replay = 1,
        )

    private val sharedListFlow: SharedFlow<List<ObjectId>> = mutableListFlow.asSharedFlow()

    override val detailOrderState: StateFlow<List<ObjectId>>
        get() = sharedListFlow.stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )

    private var _list: List<ObjectId> = emptyList()
        set(value) {
            field = value
            CoroutineScope(Dispatchers.Default).launch {
                mutableListFlow.emit(value)
            }
        }
    override val list: List<ObjectId>
        get() {
            return _list
        }

    override val isEmpty: Boolean
        get() = _list.isEmpty()

    private val config: RealmConfiguration =
        RealmConfiguration.create(schema = setOf(RealmDetailOrder::class))
    private val realm: Realm = Realm.open(config)

    override var isLoading: Boolean = false

    override var titleId: ObjectId? = null
        set(value) {
            isLoading = true
            field = value
            CoroutineScope(Dispatchers.Default).launch {
                if (value == null) {
                    mutableListFlow.emit(emptyList())
                    return@launch
                }
                val list = realm
                    .query<RealmDetailOrder>("titleId == $0", titleId)
                    .find()
                if (list.isEmpty()) {
                    mutableListFlow.emit(emptyList())
                    return@launch
                }
                val orderList = list.first().oderList
                isLoading = false
                _list = orderList
            }
        }

    override fun update(
        titleId: ObjectId,
        list: List<ObjectId>,
    ) {
        _list = list
        realm.writeBlocking {
            val detailOrder = realm
                .query<RealmDetailOrder>(
                    "titleId == $0",
                    titleId
                )
                .first()
                .find()

            if (detailOrder == null) {
                // データがなかったので新規作成
                val itemOrder = RealmDetailOrder().apply {
                    this.titleId = titleId
                    this.oderList = list.toRealmList()
                }
                copyToRealm(itemOrder)
            } else {
                // データがあったのでアップデート
                findLatest(detailOrder)?.apply {
                    this.oderList = list.toRealmList()
                }
            }
        }
    }

    override fun delete(titleId: ObjectId) {
        TODO("Not yet implemented")
    }
}
