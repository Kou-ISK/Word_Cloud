package service

import com.kennycason.kumo.CollisionMode
import com.kennycason.kumo.WordCloud
import com.kennycason.kumo.WordFrequency
import com.kennycason.kumo.bg.RectangleBackground
import com.kennycason.kumo.font.scale.LinearFontScalar
import com.kennycason.kumo.image.AngleGenerator
import com.kennycason.kumo.palette.LinearGradientColorPalette
import properties.Properties.Companion.WORD_CLOUD_OUTPUT_PATH
import java.awt.Color
import java.awt.Dimension

class WordCloud(wordFrequencyMap: MutableMap<String, Int>) {
    private var wordFrequencyMap: MutableMap<String, Int>

    init {
        this.wordFrequencyMap = wordFrequencyMap
    }

    fun draw() {
//        https://masato-ka.hatenablog.com/entry/2019/03/12/231009
        val wordFrequencies: List<WordFrequency> = wordFrequencyMap.entries
            .map { data -> WordFrequency(data.key, data.value) }

        val dimension = Dimension(800, 450) //  (1)
        val wordCloud = WordCloud(dimension, CollisionMode.PIXEL_PERFECT) // (2)
        wordCloud.setPadding(1)
        wordCloud.setAngleGenerator(AngleGenerator(0))
        wordCloud.setBackground(RectangleBackground(dimension)) // (3)
        wordCloud.setBackgroundColor(Color.white) // (4)
        wordCloud.setColorPalette(
            LinearGradientColorPalette(
                Color.BLUE, Color.GREEN,
                30,
            )
        ) // (5)

        wordCloud.setFontScalar(LinearFontScalar(30, 300)) // (6)
        wordCloud.build(wordFrequencies) // (7)
        wordCloud.writeToFile(WORD_CLOUD_OUTPUT_PATH)
    }
}