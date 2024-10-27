package usecase.getitemorder

import org.mongodb.kbson.ObjectId

interface GetItemOrderUseCase {
    fun invoke(): List<ObjectId>
}
