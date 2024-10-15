package repository.screentype

import kotlinx.coroutines.flow.MutableSharedFlow

interface ScreenTypeRepository {
    val screenTypeFlow: MutableSharedFlow<String?>
    var screenType: String?
}
