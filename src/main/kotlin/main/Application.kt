package main

import service.Analyze
import service.Scraping
import service.WordCloud

fun main(args: Array<String>) {
    val inputText = Scraping().getTextFromMyNote()
    val analyze = Analyze(inputText)
    println(analyze.getNoun())
    val frequencyMap = analyze.countFrequency(analyze.getNoun())
    WordCloud(frequencyMap).draw()
}