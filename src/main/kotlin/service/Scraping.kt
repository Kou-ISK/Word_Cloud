package service

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

class Scraping {
    fun getTextFromMyNote(): String {
        // 環境変数にchromedriverのパスを設定
        System.setProperty("webdriver.chrome.driver", "/Users/isakakou/Documents/workSpace/Word_Cloud/chromedriver")

        val options = ChromeOptions().apply {
            // headlessモードにする場合はここで指定
            addArguments("--headless")
        }

        val driver: WebDriver = ChromeDriver(options)
        // Webページにアクセス
        driver.get("https://note.com/kou_isk")
        // TODO 「記事をもっと見る」ボタンをクリックできるようにする
        driver.findElement(By.xpath("/html/body/div/div/div/div[1]/main/div/div[4]/div[2]/div/div/div/section[3]/div/div/div/button"))
            .click()
        var noteList = driver.findElement(By.className("o-noteList")).findElements(By.tagName("a"))
        var text = ""
        // TODO スクレイピングのメソッドが異常終了しないように修正する
        for (i in 0..noteList.size) {
            driver.get("https://note.com/kou_isk")
            Thread.sleep(1000)
            noteList = driver.findElement(By.className("o-noteList")).findElements(By.tagName("a"))
            Thread.sleep(3000)
            noteList[i].click()
            println("==============================")
            Thread.sleep(3000)
            driver.findElements(By.tagName("p")).forEach { p ->
                text += p.text.trimIndent().trim().replace("\\n", "")
                println(p.text.trimIndent().trim().replace("\\n", ""))
            }
            Thread.sleep(3000)
        }

        // ブラウザを閉じる
        driver.close()
        return text
    }
}