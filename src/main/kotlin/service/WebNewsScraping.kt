package service

import org.openqa.selenium.By
import properties.DriverProperties
import properties.DriverProperties.Companion.driver
import properties.Properties.Companion.YAHOO_NEWS_URI

class WebNewsScraping {
    fun getFromYahooNews() {
        DriverProperties().driverSetting()
        driver.get(YAHOO_NEWS_URI)
        val category = driver.findElement(By.id("snavi")).findElement(By.tagName("ul")).findElements(By.tagName("li"))
        category.forEach {
            it.findElement(By.tagName("a")).click()
            val pTag = driver.findElements(By.tagName("a"))
            pTag.forEach { println(it.text) }
            // TODO Topから遷移した際のページレイアウトが変わるのでそれに対応する
        }
    }
}