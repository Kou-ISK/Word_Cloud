package service

import com.atilika.kuromoji.ipadic.Token
import com.atilika.kuromoji.ipadic.Tokenizer
import java.util.*


class Analyze(inputText: String) {
    private var tokens: MutableList<Token>

    init {
        val tokenizer = Tokenizer()
        tokens = tokenizer.tokenize(inputText)
    }

    fun getThisPart(part: String): List<String>? {
        return when (part) {
            "名詞", "助詞", "助動詞", "動詞", "記号", "連体詞", "形容詞", "副詞", "接頭詞", "接続詞", "感動詞" -> {
                tokens.filter { it.partOfSpeechLevel1.equals(part) }.map { it.baseForm }
            }

            "副詞可能", "係助詞", "一般", "接尾", "格助詞", "形容動詞語幹", "*", "非自立", "自立", "接続助詞", "数", "句点", "読点", "副詞化", "連体化", "サ変接続", "並立助詞", "助詞類接続", "固有名詞", "空白", "副助詞", "名詞接続", "副助詞／並立助詞／終助詞", "括弧開", "括弧閉", "終助詞", "代名詞", "動詞非自立的", "ナイ形容詞語幹", "接続詞的", "数接続" -> {
                tokens.filter { it.partOfSpeechLevel2.equals(part) }.map { it.baseForm }
            }

            "形容動詞語幹", "一般", "引用", "副詞可能", "組織", "連語", "人名", "助数詞", "助動詞語幹", "地域", "特殊", "サ変接続" -> {
                tokens.filter { it.partOfSpeechLevel3.equals(part) }.map { it.baseForm }
            }

            "姓", "名", "一般", "国" -> {
                tokens.filter { it.partOfSpeechLevel4.equals(part) }.map { it.baseForm }
            }

            else -> {
                println(part)
                return null
            }
        }
    }

    fun segment(): List<String> {
        return tokens.map { it.baseForm }
    }

    fun countFrequency(wordList: List<String>): MutableMap<String, Int> {
        val wordFrequencyMap: MutableMap<String, Int> = HashMap()
        for (s in wordList) {
            if (s != "*") {
                var count = wordFrequencyMap[s]
                if (count == null) count = 0
                wordFrequencyMap[s] = count + 1
            }
        }
        return wordFrequencyMap
    }

    fun split() {
        for (token in tokens) {
            println(token.surface + "：" + token.partOfSpeechLevel1)
        }
    }
}