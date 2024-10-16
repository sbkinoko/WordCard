package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.screentype.ScreenTypeRepository

class TopViewModel : KoinComponent {
    private val screenTypeRepository: ScreenTypeRepository by inject()

    private val _groupsFlow = MutableStateFlow<List<String>>(listOf())
    val groupsFlow: StateFlow<List<String>> = _groupsFlow.asStateFlow()

    fun onClick(text: String) {
        screenTypeRepository.screenType = text
    }

    private var num = 0
    fun addGroup() {
        _groupsFlow.value += "num:$num"
        num++
    }

    fun deleteAt(index: Int) {
        val list = _groupsFlow.value.toMutableList()
        list.removeAt(index)
        _groupsFlow.value = list
    }
}
