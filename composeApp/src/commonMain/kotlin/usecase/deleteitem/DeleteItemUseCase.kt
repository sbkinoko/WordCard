package usecase.deleteitem

import org.mongodb.kbson.ObjectId

interface DeleteItemUseCase {
    operator fun invoke(
        id: ObjectId,
    )
}
