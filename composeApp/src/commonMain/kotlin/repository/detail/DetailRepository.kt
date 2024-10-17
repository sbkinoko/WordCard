package repository.detail

import domain.Detail
import kotlinx.coroutines.flow.StateFlow

interface DetailRepository {
    val detailListState: StateFlow<List<Detail>>

    fun add()

    fun updateAt(
        index: Int,
        front: String,
        back: String,
        color: String,
    )

    fun deleteAt(
        index: Int,
    )
}
