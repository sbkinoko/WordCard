package repository.screentype

import domain.ScreenType
import domain.Title
import kotlinx.coroutines.flow.StateFlow

interface ScreenTypeRepository {
    val titleFlow: StateFlow<Title?>
    var title: Title?

    val screenType: StateFlow<ScreenType>
    suspend fun setScreenType(screenType: ScreenType)
}
