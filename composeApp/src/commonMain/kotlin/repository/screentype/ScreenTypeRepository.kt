package repository.screentype

import domain.Title
import kotlinx.coroutines.flow.MutableSharedFlow

interface ScreenTypeRepository {
    val screenTypeFlow: MutableSharedFlow<Title?>
    var screenType: Title?
}
