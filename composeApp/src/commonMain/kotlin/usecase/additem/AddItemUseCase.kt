package usecase.additem

import org.mongodb.kbson.ObjectId

interface AddItemUseCase {
    operator fun invoke(
        objectId: ObjectId,
    ): List<ObjectId>
}
