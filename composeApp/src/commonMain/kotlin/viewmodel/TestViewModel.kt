package viewmodel

import domain.Detail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.detail.DetailRepository
import kotlin.random.Random

class TestViewModel : KoinComponent {
    private val detailRepository: DetailRepository by inject()

    private var list = listOf<Detail>()

    private val mutableQuestion: MutableStateFlow<Detail>

    val question: StateFlow<Detail>
        get() = mutableQuestion.asStateFlow()

    private val _showAnswer = MutableStateFlow(false)
    val showAnswer: StateFlow<Boolean> = _showAnswer.asStateFlow()

    private val _answer = MutableStateFlow("")
    val answer: StateFlow<String> = _answer.asStateFlow()

    init {
        list = detailRepository.list
        mutableQuestion = MutableStateFlow(
            list[0]
        )
    }

    fun showAnswer() {
        _showAnswer.value = true
    }

    fun goNext() {
        _showAnswer.value = false
        mutableQuestion.value = list[Random.nextInt(list.size)]
    }
}
