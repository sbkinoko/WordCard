package usecase.getorder

import org.mongodb.kbson.ObjectId

interface GetItemIndexUseCase {
    operator fun invoke(
        objectId: ObjectId,
    ): Int
}
