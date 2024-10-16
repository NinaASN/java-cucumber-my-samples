package support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class DriverFactory {
    //for parallel execution:
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    //not parallel
    //private static final WebDriver driver = new ChromeDriver();

    public static WebDriver getDriver(){
        if(driverThreadLocal.get() == null){
            driverThreadLocal.set(new ChromeDriver());
        }
        return driverThreadLocal.get();
    }

    public static void quitDriver(){
        if(driverThreadLocal.get() != null){
            driverThreadLocal.get().quit();
            driverThreadLocal.remove();
        }
    }
}
