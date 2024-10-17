package repository.screentype

import domain.Title
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ScreenTypeRepositoryImpl : ScreenTypeRepository {
    override val screenTypeFlow: MutableSharedFlow<Title?> = MutableStateFlow(null)

    override var screenType: Title? = null
        set(value) {
            field = value
            CoroutineScope(Dispatchers.Default).launch {
                screenTypeFlow.emit(value)
            }
        }
}
