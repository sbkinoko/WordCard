package repository.screentype

import domain.ScreenType
import domain.Title
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ScreenTypeRepositoryImpl : ScreenTypeRepository {
    private val _titleFlow: MutableSharedFlow<Title?> = MutableStateFlow(null)
    override val titleFlow: StateFlow<Title?> = _titleFlow.stateIn(
        scope = CoroutineScope(Dispatchers.Default),
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(),
        initialValue = null
    )

    override var title: Title? = null
        set(value) {
            field = value
            CoroutineScope(Dispatchers.Default).launch {
                _titleFlow.emit(value)
            }
        }

    private val _screenType: MutableSharedFlow<ScreenType> =
        MutableSharedFlow(
            replay = 1,
        )
    override val screenType: StateFlow<ScreenType> = _screenType.stateIn(
        scope = CoroutineScope(Dispatchers.Default),
        started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(),
        initialValue = ScreenType.EDIT
    )

    override suspend fun setScreenType(screenType: ScreenType) {
        _screenType.emit(screenType)
    }
}
