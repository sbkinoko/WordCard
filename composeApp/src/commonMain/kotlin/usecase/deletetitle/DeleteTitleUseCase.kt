package usecase.deletetitle

import org.mongodb.kbson.ObjectId

interface DeleteTitleUseCase {
    operator fun invoke(
        titleId: ObjectId,
    )
}
