package usecase.getitemorder

import domain.Detail

interface GetIOrderedItemUseCase {
    fun invoke(): List<Detail>
}
