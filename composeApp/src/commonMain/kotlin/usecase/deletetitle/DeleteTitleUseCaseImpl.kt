package usecase.deletetitle

import org.mongodb.kbson.ObjectId
import repository.detail.DetailRepository
import repository.detailorder.DetailOrderRepository
import repository.title.TitleRepository

class DeleteTitleUseCaseImpl(
    private val titleRepository: TitleRepository,
    private val detailRepository: DetailRepository,
    private val detailOrderRepository: DetailOrderRepository,
) : DeleteTitleUseCase {
    override fun invoke(titleId: ObjectId) {
        val list = detailOrderRepository.getItemOrder(titleId)
        list.forEach {
            detailRepository.deleteAt(id = it)
        }
        detailOrderRepository.delete(titleId)
        titleRepository.delete(id = titleId)
    }
}
