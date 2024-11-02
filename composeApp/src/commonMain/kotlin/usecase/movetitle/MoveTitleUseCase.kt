package usecase.movetitle

import org.mongodb.kbson.ObjectId

interface MoveTitleUseCase {
    operator fun invoke(
        id: ObjectId,
        index: String,
    )
}
