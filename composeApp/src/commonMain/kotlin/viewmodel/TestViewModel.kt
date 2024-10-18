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

    private var matchList: MutableList<Boolean> = mutableListOf()

    private val _input = MutableStateFlow("")
    val input: StateFlow<String> = _input.asStateFlow()

    init {
        list = detailRepository.list
        mutableQuestion = MutableStateFlow(
            list[0]
        )
        matchList = MutableList(mutableQuestion.value.back.length) { false }
    }

    fun reset() {
        _showAnswer.value = false
        _input.value = ""
        list = detailRepository.list
        mutableQuestion.value = list[0]
        matchList = MutableList(mutableQuestion.value.back.length) { false }
    }

    fun updateInput(text: String) {
        _input.value = text
    }

    fun showAnswer() {
        _showAnswer.value = true
    }

    fun goOK() {
        go()
    }

    fun goNG() {
        go()
    }

    private fun go() {
        _showAnswer.value = false
        mutableQuestion.value = list[Random.nextInt(list.size)]
        matchList = MutableList(mutableQuestion.value.back.length) { false }

    }

    fun open() {
        val text = input.value

        if (text.isEmpty()) {
            return
        }

        var searchindex = 0
        // 入力文字列を含む間
        while (true) {
            val matchIndex = question.value.back.indexOf(text, searchindex)
            if (matchIndex == -1) {
                break
            }
            searchindex = matchIndex + text.length
            for (i in matchIndex until matchIndex + text.length) {
                matchList[i] = true
            }
        }
        _answer.value = matchList.mapIndexed { index, flag ->
            if (flag) {
                question.value.back[index].toString()
            } else {
                "　"
            }
        }.joinToString("")

        if (matchList.all { it }) {
            showAnswer()
        }
    }
}
