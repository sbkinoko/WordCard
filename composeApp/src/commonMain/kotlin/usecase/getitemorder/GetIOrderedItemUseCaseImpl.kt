package usecase.getitemorder

import domain.Detail
import repository.detail.DetailRepository
import repository.detailorder.DetailOrderRepository
import repository.screentype.ScreenTypeRepository

class GetIOrderedItemUseCaseImpl(
    private val screenTypeRepository: ScreenTypeRepository,
    private val detailRepository: DetailRepository,
    private val detailOrderRepository: DetailOrderRepository,
) : GetIOrderedItemUseCase {
    override fun invoke(): List<Detail> {
        val titleId = screenTypeRepository.title!!.id
        val list = detailOrderRepository.getItemOrder(
            titleId = titleId,
        )
        return list.mapNotNull {
            detailRepository.getDetail(
                objectId = it,
            )
        }
    }
}
