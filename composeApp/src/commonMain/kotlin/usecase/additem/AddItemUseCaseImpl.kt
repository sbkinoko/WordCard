package usecase.additem

import repository.detail.DetailRepository
import repository.detailorder.DetailOrderRepository
import repository.screentype.ScreenTypeRepository

class AddItemUseCaseImpl(
    private val detailRepository: DetailRepository,
    private val detailOrderRepository: DetailOrderRepository,
    private val screenTypeRepository: ScreenTypeRepository,
) : AddItemUseCase {
    override fun invoke() {
        val titleId = screenTypeRepository.title!!.id
        detailRepository.add(
            titleId = titleId,
        )
        detailOrderRepository.update(
            titleId = titleId,
            list = detailOrderRepository.list + detailRepository.list.last().id
        )
    }
}
