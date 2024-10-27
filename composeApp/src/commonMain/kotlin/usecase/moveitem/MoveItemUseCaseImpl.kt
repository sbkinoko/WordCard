package usecase.moveitem

import org.mongodb.kbson.ObjectId
import repository.detailorder.DetailOrderRepository
import repository.screentype.ScreenTypeRepository

class MoveItemUseCaseImpl(
    private val detailOrderRepository: DetailOrderRepository,
    private val screenTypeRepository: ScreenTypeRepository,
) : MoveItemUseCase {
    override fun invoke(
        id: ObjectId,
        index: String,
    ) {
        val indexNum = index.toIntOrNull() ?: return

        if (indexNum < 0) {
            return
        }

        val titleId = screenTypeRepository.title!!.id
        val list = detailOrderRepository.getItemOrder(
            titleId = titleId,
        )

        val newList = list.filter {
            it != id
        }.toMutableList()

        // 最後尾より大きな数字を指定している場合は最後に移動
        if (indexNum >= list.size) {
            detailOrderRepository.update(
                titleId = titleId,
                list = list + id
            )
            return
        }

        // 指定した位置に移動
        newList.add(indexNum, id)
        detailOrderRepository.update(
            titleId = titleId,
            list = newList.toList()
        )
    }
}
