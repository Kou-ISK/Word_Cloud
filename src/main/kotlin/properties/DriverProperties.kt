package properties

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

class DriverProperties {
    companion object {
        lateinit var driver: WebDriver
    }

    fun driverSetting() {
        // 環境変数にchromedriverのパスを設定
        System.setProperty("webdriver.chrome.driver", Properties.CHROME_DRIVER_PATH)

        val options = ChromeOptions().apply {
            // headlessモードにする場合はここで指定
            addArguments("--headless")
        }
        driver = ChromeDriver(options)
    }
}