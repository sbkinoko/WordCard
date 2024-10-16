package viewmodel

import domain.Title
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.screentype.ScreenTypeRepository

class TopViewModel : KoinComponent {
    private val screenTypeRepository: ScreenTypeRepository by inject()

    private val _groupsFlow: MutableStateFlow<List<Title>> = MutableStateFlow(listOf())
    val groupsFlow: StateFlow<List<Title>> = _groupsFlow.asStateFlow()

    fun onClick(text: String) {
        screenTypeRepository.screenType = text
    }

    private var num = 0
    fun addGroup() {
        _groupsFlow.value += Title()
        num++
    }

    fun deleteAt(index: Int) {
        val list = _groupsFlow.value.toMutableList()
        list.removeAt(index)
        _groupsFlow.value = list
    }

    fun editTitle(
        index: Int,
        title: String,
    ) {
        val list = _groupsFlow.value.toMutableList()
        list[index] = list[index].copy(
            title = title
        )

        _groupsFlow.value = list.toList()
    }

    fun save(index: Int) {

    }
}
