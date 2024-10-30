package usecase.getorder

import org.mongodb.kbson.ObjectId
import repository.detailorder.DetailOrderRepository
import repository.screentype.ScreenTypeRepository

class GetItemIndexUseCaseImpl(
    private val detailOrderRepository: DetailOrderRepository,
    private val screenTypeRepository: ScreenTypeRepository,
) : GetItemIndexUseCase {
    override fun invoke(objectId: ObjectId): Int {
        val titleId = screenTypeRepository.title?.id
            ?: return -1
        val list = detailOrderRepository.getItemOrder(titleId)
        return list.indexOfFirst {
            it == objectId
        }
    }
}
