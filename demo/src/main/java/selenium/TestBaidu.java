package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by johnny on 2016/9/30.
 * Demo of selenium
 */
public class TestBaidu {
    public static void main(String[] args) {
        TestBaidu testBaidu = new TestBaidu();
        testBaidu.testChrome();
        testBaidu.testIE();
        testBaidu.testFirefox();
    }

    private void testChrome() {
        System.setProperty("webdriver.chrome.driver", "D:\\Java_ex\\test\\src\\test\\resources\\chromedriver.exe");
        WebDriver dr = new ChromeDriver();
        dr.get("https://www.baidu.com");
        dr.findElement(By.id("kw")).sendKeys("Selenium");
        dr.findElement(By.id("su")).click();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dr.quit();
    }

    private void testIE() {
        System.setProperty("webdriver.ie.driver", "D:\\Java_ex\\test\\src\\test\\resources\\IEDriverServer.exe");
        //IE explorer security setting problem
        DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
        ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
        WebDriver dr = new InternetExplorerDriver(ieCapabilities);
        dr.get("https://www.baidu.com");
        dr.findElement(By.id("kw")).sendKeys("Selenium");
        dr.findElement(By.id("su")).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dr.quit();
    }

    private void testFirefox() {
        WebDriver dr = new FirefoxDriver();
        dr.get("https://www.baidu.com");
        dr.findElement(By.id("kw")).sendKeys("helloSelenium");
        dr.findElement(By.id("su")).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dr.quit();
    }

}