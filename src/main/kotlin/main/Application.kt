package main

import properties.Properties.Companion.INPUT_FILE_PATH
import service.Analyze
import service.Scraping
import service.WordCloud
import java.io.File

fun main(args: String) {
    //val inputText = Scraping().getTextFromMyNote()
    val inputText =
        File(INPUT_FILE_PATH).readText().trimIndent().trim().replace("\\n", "")
    val analyze = Analyze(inputText)
    println(analyze.getNoun2())
    println(analyze.getAssociation())
    val frequencyMap = analyze.countFrequency(analyze.getNoun())
    WordCloud(frequencyMap).draw()
}