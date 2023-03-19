package service

import org.openqa.selenium.By
import properties.DriverProperties
import properties.DriverProperties.Companion.driver
import properties.Properties.Companion.YAHOO_NEWS_URI

class WebNewsScraping {
    fun getFromYahooNews(): String {
        DriverProperties().driverSetting()
        driver.get(YAHOO_NEWS_URI)
        val category =
            driver.findElement(By.className("yjnHeader_sub_cat")).findElements(By.tagName("li"))
        val textList: MutableList<String> = mutableListOf()
        for (i in 0 until category.size) {
            val category =
                driver.findElement(By.className("yjnHeader_sub_cat")).findElements(By.tagName("li"))
            try {
                category[i].findElement(By.tagName("a")).click()
                val topics = driver.findElement(By.id("uamods-topics")).findElements(By.tagName("li"))
                topics.forEach {
                    println(it.text)
                    textList += it.text
                }
            } catch (e: Exception) {
                println(e)
            } finally {
                println("============================")
            }
        }
        return textList.distinct().toString()
    }
}