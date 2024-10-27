package usecase.getitemorder

import org.mongodb.kbson.ObjectId
import repository.detailorder.DetailOrderRepository
import repository.screentype.ScreenTypeRepository

class GetItemOrderUseCaseImpl(
    private val screenTypeRepository: ScreenTypeRepository,
    private val detailOrderRepository: DetailOrderRepository,
) : GetItemOrderUseCase {
    override fun invoke(): List<ObjectId> {
        val titleId = screenTypeRepository.title!!.id
        return detailOrderRepository.getItemOrder(
            titleId = titleId,
        )
    }
}
