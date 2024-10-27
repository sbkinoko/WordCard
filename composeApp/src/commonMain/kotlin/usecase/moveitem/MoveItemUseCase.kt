package usecase.moveitem

import org.mongodb.kbson.ObjectId

interface MoveItemUseCase {
    operator fun invoke(
        id: ObjectId,
        index: String,
    )
}
