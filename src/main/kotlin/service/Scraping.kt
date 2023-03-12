package service

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import java.io.File


class Scraping {
    private val driver: WebDriver = ChromeDriver()
    fun getTextFromMyNote(): String {
        // 環境変数にchromedriverのパスを設定
        System.setProperty("webdriver.chrome.driver", "/Users/isakakou/Documents/workSpace/Word_Cloud/chromedriver")

//        val options = ChromeOptions().apply {
//            // headlessモードにする場合はここで指定
//            addArguments("--headless")
//        }
        // Webページにアクセス
        driver.get("https://note.com/kou_isk")
        Thread.sleep(1000)
        loadMoreContent()
        val noteSize = driver.findElement(By.className("o-noteList"))
            .findElements(By.className("m-largeNoteWrapper__link")).size
        println(noteSize)
        var text = ""
        val file = File("/Users/isakakou/Desktop/名称未設定フォルダ/note_text.txt")
        // TODO スクレイピングのメソッドが異常終了しないように修正する
        var i = 0
        while (i < noteSize) {
            driver.get("https://note.com/kou_isk")
            Thread.sleep(1000)
            if (i > 11) loadMoreContent()
            println("$i 番目")
            val noteList = driver.findElement(By.className("o-noteList"))
                .findElements(By.className("m-largeNoteWrapper__link"))
            Thread.sleep(4000)
            noteList[i].click()
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
            driver.executeScript("javascript:window.scrollBy(0,-80)") //scrollIntoView(true)だけだとスクロールしすぎるので、少し戻す
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
