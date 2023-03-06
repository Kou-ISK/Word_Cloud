package service

import com.kennycason.kumo.CollisionMode
import com.kennycason.kumo.WordCloud
import com.kennycason.kumo.WordFrequency
import com.kennycason.kumo.bg.CircleBackground
import com.kennycason.kumo.font.scale.LinearFontScalar
import com.kennycason.kumo.palette.LinearGradientColorPalette
import java.awt.Color
import java.awt.Dimension
import java.io.File
import javax.imageio.ImageIO

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
        wordCloud.setPadding(2)
        wordCloud.setBackground(CircleBackground(200)) // (3)
        wordCloud.setBackgroundColor(Color.white) // (4)
        wordCloud.setColorPalette(
            LinearGradientColorPalette(
                Color.RED, Color.BLUE, Color.GREEN,
                30, 30
            )
        ) // (5)
        wordCloud.setFontScalar(LinearFontScalar(10, 40)) // (6)
        wordCloud.build(wordFrequencies) // (7)
        val resultImage = wordCloud.bufferedImage // (8)
        ImageIO.write(resultImage, "png", File("/Users/isakakou/Desktop/名称未設定フォルダ/word_cloud.png"))
    }
}