package usecase.additem

import org.mongodb.kbson.ObjectId
import repository.detail.DetailRepository
import repository.detailorder.DetailOrderRepository
import repository.screentype.ScreenTypeRepository

class AddItemUseCaseImpl(
    private val detailRepository: DetailRepository,
    private val detailOrderRepository: DetailOrderRepository,
    private val screenTypeRepository: ScreenTypeRepository,
) : AddItemUseCase {
    override fun invoke(): List<ObjectId> {
        val titleId = screenTypeRepository.title!!.id
        val objectId = ObjectId()
        detailRepository.add(
            titleId = titleId,
            id = objectId,
        )

        val updatedList =
            detailOrderRepository.getItemOrder(titleId) + objectId
        detailOrderRepository.update(
            titleId = titleId,
            list = updatedList,
        )
        return updatedList
    }
}
