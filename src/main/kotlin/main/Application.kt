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
    val frequencyMap = analyze.countFrequency(analyze.getThisPart("名詞") ?: throw Exception())
    WordCloud(frequencyMap).draw()
}