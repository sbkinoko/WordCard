package repository.detail

import domain.Detail
import kotlinx.coroutines.flow.StateFlow

interface DetailRepository {
    val detailListState: StateFlow<List<Detail>>

    fun add()

    fun updateAt(
        id: Int,
        front: String,
        back: String,
        color: String,
    )

    fun deleteAt(
        id: Int,
    )
}
