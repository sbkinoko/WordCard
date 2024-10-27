package usecase.deleteitem

import org.mongodb.kbson.ObjectId
import repository.detail.DetailRepository
import repository.detailorder.DetailOrderRepository
import repository.screentype.ScreenTypeRepository

class DeleteItemUseCaseImpl(
    private val detailRepository: DetailRepository,
    private val detailOrderRepository: DetailOrderRepository,
    private val screenTypeRepository: ScreenTypeRepository,
) : DeleteItemUseCase {
    override fun invoke(id: ObjectId) {
        val titleId = screenTypeRepository.title!!.id
        detailRepository.deleteAt(id)
        detailOrderRepository.update(
            titleId = titleId,
            list = detailOrderRepository.getItemOrder(
                titleId = titleId,
            ).filter {
                it != id
            }
        )
    }
}
