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

    fun getNoun(): List<String> {
        return tokens.filter { it.partOfSpeechLevel1.equals("名詞") }.map { it.baseForm }
    }

    fun getVerb(): List<String> {
        return tokens.filter { it.partOfSpeechLevel1.equals("動詞") }.map { it.baseForm }
    }

    fun getAdjective(): List<String> {
        return tokens.filter { it.partOfSpeechLevel1.equals("形容詞") }.map { it.baseForm }
    }

    fun getAdnominal(): List<String> {
        return tokens.filter { it.partOfSpeechLevel1.equals("連体詞") }.map { it.baseForm }
    }

    fun segment(): List<String> {
        return tokens.map { it.baseForm }
    }

    fun countFrequency(wordList: List<String>): MutableMap<String, Int> {
        val wordFrequencyMap: MutableMap<String, Int> = HashMap()
        for (s in wordList) {
            var count = wordFrequencyMap[s]
            if (count == null) count = 0
            wordFrequencyMap[s] = count + 1
        }
        return wordFrequencyMap
    }

    fun split() {
        for (token in tokens) {
            println(token.surface + "：" + token.partOfSpeechLevel1)
        }
    }
}