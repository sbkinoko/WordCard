package usecase.movetitle

import org.mongodb.kbson.ObjectId
import repository.titleorder.TitleOrderRepository

class MoveTitleUseCaseImpl(
    private val titleOrderRepository: TitleOrderRepository,
) : MoveTitleUseCase {
    override fun invoke(
        id: ObjectId,
        index: String,
    ) {
        val indexNum = index.toIntOrNull() ?: return

        if (indexNum < 0) {
            return
        }

        val list = titleOrderRepository.getItemOrder()

        val newList = list.filter {
            it != id
        }.toMutableList()

        // 最後尾より大きな数字を指定している場合は最後に移動
        if (indexNum >= list.size) {
            titleOrderRepository.update(
                newList = newList + id
            )
            return
        }

        // 指定した位置に移動
        newList.add(indexNum, id)
        titleOrderRepository.update(
            newList = newList.toList()
        )
    }
}
