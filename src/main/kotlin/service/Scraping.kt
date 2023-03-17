package service

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import properties.Properties.Companion.CHROME_DRIVER_PATH
import properties.Properties.Companion.NOTE_ROOT_URI
import properties.Properties.Companion.NOTE_USER_NAME
import properties.Properties.Companion.OUTPUT_FILE_PATH
import java.io.File


class Scraping {
    private lateinit var driver: WebDriver
    private val NOTE_URI = NOTE_ROOT_URI + NOTE_USER_NAME

    private fun driverSetting() {
        // 環境変数にchromedriverのパスを設定
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH)

        val options = ChromeOptions().apply {
            // headlessモードにする場合はここで指定
            addArguments("--headless")
        }
        driver = ChromeDriver(options)
    }

    fun getTextFromMyNote(): String {
        driverSetting()
        // Webページにアクセス
        driver.get(NOTE_URI)
        Thread.sleep(1000)
        loadMoreContent()
        val noteSize = driver.findElement(By.className("o-noteList"))
            .findElements(By.className("m-largeNoteWrapper__link")).size
        println(noteSize)
        var text = ""
        val file = File(OUTPUT_FILE_PATH)
        // TODO スクレイピングのメソッドが異常終了しないように修正する
        var i = 0
        while (i < noteSize) {
            driver.get(NOTE_URI)
            Thread.sleep(1000)
            if (i > 11) loadMoreContent()
            println("$i 番目")
            val noteList = driver.findElement(By.className("o-noteList"))
                .findElements(By.className("m-largeNoteWrapper__link"))
            Thread.sleep(4000)
            val element = noteList[i]
            (driver as JavascriptExecutor).executeScript("arguments[0].click();", element)
            println("==============================")
            Thread.sleep(2000)
            try {
                driver.findElement(By.className("note-common-styles__textnote-body")).findElements(By.tagName("p"))
                    .forEach { p ->
                        text += p.text.trimIndent().trim().replace("\\n", "")
                        println(p.text.trimIndent().trim().replace("\\n", ""))
                    }
                Thread.sleep(2000)
                file.writeText(text, Charsets.UTF_8)
            } catch (e: Exception) {
                println(e)
            } finally {
                i += 1
            }
        }

        // ブラウザを閉じる
        driver.close()
        return text
    }

    private fun loadMoreContent() {
        val element = driver.findElement(
            By.className("o-loadMoreButton")
        )
        while (element != null) {
            (driver as JavascriptExecutor).executeScript(
                "arguments[0].scrollIntoView(true);",
                element
            )
            (driver as JavascriptExecutor).executeScript("javascript:window.scrollBy(0,-80)") //scrollIntoView(true)だけだとスクロールしすぎるので、少し戻す
            try {
                element.click()
                Thread.sleep(2000)
            } catch (e: Exception) {
                println(e)
                break
            }
        }
        Thread.sleep(3000)
    }
}
