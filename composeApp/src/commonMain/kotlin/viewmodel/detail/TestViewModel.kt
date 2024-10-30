package viewmodel.detail

import domain.Constant
import domain.Detail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mongodb.kbson.ObjectId
import repository.detail.DetailRepository
import repository.screentype.ScreenTypeRepository
import kotlin.random.Random

class TestViewModel : KoinComponent {
    private val detailRepository: DetailRepository by inject()
    private val screenTypeRepository: ScreenTypeRepository by inject()

    private val mutableQuestion: MutableStateFlow<Detail> =
        MutableStateFlow(
            Detail(
                id = ObjectId(),
                titleId = ObjectId(),
                front = "",
                back = "",
                color = "",
                resultList = listOf(0),
            )
        )

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
        reset()
    }

    fun reset() {
        _showAnswer.value = false
        _input.value = ""
        _answer.value = ""

        val list = detailRepository.getItems(
            titleId = screenTypeRepository.title!!.id,
        )

        if (list.isEmpty()) {
            return
        }

        try {
            mutableQuestion.value = list[getQuestionId(list)]
            matchList = MutableList(mutableQuestion.value.back.length) { false }
        } catch (
            _: RuntimeException
        ) {
            // 何もしない
        }
    }

    private fun getQuestionId(list: List<Detail>): Int {
        var sum = 0
        val questionList = list.map {
            // 誤答の数の2乗
            // ただし、全問正解していると出題されなくなるので1は加算する
            val num = Constant.RESULT_LENGTH + 1 - it.resultList.sum()
            sum += num * num
            sum
        }

        val num = Random.nextInt(sum)
        for (i in questionList.indices) {
            if (num < questionList[i]) {
                return i
            }
        }

        // for文からreturn できないときはエラー
        throw RuntimeException()
    }

    fun updateInput(text: String) {
        _input.value = text
    }

    fun showAnswer() {
        _showAnswer.value = true
    }

    fun goOK() {
        CoroutineScope(Dispatchers.Default).launch {
            detailRepository.updateResult(
                id = question.value.id,
                result = true,
            )
            delay(50)
            go()
        }
    }

    fun goNG() {
        CoroutineScope(Dispatchers.Default).launch {
            detailRepository.updateResult(
                id = question.value.id,
                result = false,
            )
            delay(50)
            go()
        }
    }

    private fun go() {
        _showAnswer.value = false
        reset()
    }

    fun open() {
        val text = input.value

        if (text.isEmpty()) {
            return
        }

        // 結果によらず入力したものをクリアする
        _input.value = ""

        openText(text)

        if (matchList.all { it }) {
            // もしすべてが一致していたら解答を開く
            showAnswer()
        } else {
            // 一部があいてないので部分開示を表示する
            _answer.value = getOpenAnswer()
        }
    }

    private fun openText(text: String) {
        var searchindex = 0
        // 入力文字列を含む間繰り返し
        while (true) {
            val matchIndex = question.value.back.indexOf(text, searchindex)
            if (matchIndex == -1) {
                break
            }
            //一致したので、そこ以降の文字を探す
            searchindex = matchIndex + text.length
            for (i in matchIndex until matchIndex + text.length) {
                matchList[i] = true
            }
        }
    }

    private fun getOpenAnswer(): String {
        return matchList.mapIndexed { index, flag ->
            if (flag) {
                // trueなら文字をそのまま入れる
                question.value.back[index].toString()
            } else {
                // falseでも改行はそのまま入れる
                if (question.value.back[index].toString() == "\n") {
                    question.value.back[index].toString()
                } else {
                    "　"
                }
            }
        }.joinToString("")
    }
}
