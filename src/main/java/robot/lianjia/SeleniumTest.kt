package robot.lianjia

import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.interactions.Actions

/**
 * 使用selenium实现的
 * Created by tend on 2017/5/18.
 */

fun main(args: Array<String>) {

//    System.setProperty("webdriver.firefox.bin", "C:/Program Files (x86)/Mozilla Firefox/firefox.exe")
//    System.setProperty("webdriver.firefox.marionette", "C:/Program Files (x86)/Mozilla Firefox/geckodriver.exe")
//    val driver: WebDriver = FirefoxDriver()
    System.setProperty("webdriver.chrome.driver", "C:/Program Files (x86)/Google/Chrome/Application/chromedriver.exe")
    val driver = ChromeDriver()
    driver.get("http://bj.lianjia.com/xiaoqu/dongcheng/")

    while (true) {
        val links = driver.findElements(By.xpath("//div[@class='info']/div[@class='title']/a"))
        links.forEach {
            //Actions(driver).moveToElement(it).click().perform()
            it.click()
            //driver.navigate().to(it.getAttribute("href"))
            driver.switchTo().window(driver.windowHandles.filter { it != driver.windowHandle }.first())

            Thread.sleep(3000)
            val elt = driver.findElement(By.xpath("//div[@class='xiaoquInfoItem'][span[@class='xiaoquInfoLabel']='物业费用']/span[@class='xiaoquInfoContent']"))
            println(elt.text)
            driver.close()
            driver.switchTo().window(driver.windowHandles.first())
        }
//        (driver.findElement(By.linkText("下一页")) ?: break).click()//is not clickable at point (879, 676)
        val elt = driver.findElement(By.linkText("下一页")) ?: break
        elt.isDisplayed
        Actions(driver).moveToElement(elt).perform()
        driver.executeScript("window.scrollBy(0, 100)")
        elt.click()

    }
}