package main

import properties.Properties.Companion.INPUT_FILE_PATH
import service.Analyze
import service.Scraping
import service.WordCloud
import java.io.File

fun main(args: Array<String>) {
    //val inputText = Scraping().getTextFromMyNote()
    val inputText =
        File(INPUT_FILE_PATH).readText().trimIndent().trim().replace("\\n", "")
    val analyze = Analyze(inputText)
    //指定したワードをword cloudから除外
    val exclusionList = listOf("こと", "の")
    val wordList = analyze.exclude(analyze.getThisPart("名詞") ?: throw Exception(), exclusionList)
    val frequencyMap = analyze.countFrequency(wordList)
    WordCloud(frequencyMap).draw()
}