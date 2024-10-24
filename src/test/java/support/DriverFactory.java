package support;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {
    //for parallel execution:
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    //not parallel
    //private static final WebDriver driver = new ChromeDriver();

    public static WebDriver getDriver(){
        if(driverThreadLocal.get() == null){
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-fullscreen");

            driverThreadLocal.set(new ChromeDriver(options));
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
