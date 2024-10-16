package repository.title


import domain.RealmTitle
import domain.Title
import domain.toTitle
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

class TitleRepositoryImpl : TitleRepository {
    private val mutableRealmTitleFlow = MutableSharedFlow<List<Title>>()
    private val titleFlow: SharedFlow<List<Title>>
        get() = mutableRealmTitleFlow.asSharedFlow()

    override val titleState: StateFlow<List<Title>>
        get() = titleFlow.stateIn(
            scope = CoroutineScope(Dispatchers.Default),
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(),
            initialValue = titleList,
        )

    private val config: RealmConfiguration =
        RealmConfiguration.create(schema = setOf(RealmTitle::class))
    private val realm: Realm = Realm.open(config)

    override var titleList = realm.query<RealmTitle>().find().toList().map {
        it.toTitle()
    }
        set(value) {
            field = value
            CoroutineScope(Dispatchers.Default).launch {
                mutableRealmTitleFlow.emit(value)
            }
        }

    override fun updateAt(
        index: Int,
        title: String
    ) {
        val id = titleList[index].id
        val target = realm.query<RealmTitle>("id == $0", id).first().find()
            ?: return
        realm.writeBlocking {
            findLatest(
                target
            )?.apply {
                this.title = title
            }
        }

        val list = titleList.toMutableList()
        list[index] = list[index].copy(
            title = title
        )
        titleList = list
    }

    override fun add() {
        realm.writeBlocking {
            val title = RealmTitle()
            copyToRealm(
                title
            )

            val list = titleList + title.toTitle()
            titleList = list
        }
    }

    override fun deleteAt(index: Int) {
        realm.writeBlocking {
            val id = titleList[index].id

            val ob = realm.query<RealmTitle>("id == $0", id).first().find()
            if (ob != null) {
                findLatest(ob)?.let {
                    delete(it)
                }
            }

            val list = titleList.toMutableList()
            list.removeAt(index)
            titleList = list
        }
    }
}
