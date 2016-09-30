package org.framework.controller;
import cucumber.api.Scenario;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.cucumberjvm.AllureRunListener;
import java.net.URL;

/**
 * Created by hemanthsridhar on 9/22/16.
 */
public class MainController extends AllureRunListener{

    private static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();


    public String getApplicationURL(){

        return System.getProperty("applicationURL");
    }

    @Before
    public void setUp() throws Exception{

        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setBrowserName(System.getProperty("browser"));
        dc.setJavascriptEnabled(true);
        driver.set(new RemoteWebDriver(new URL(System.getProperty("url")), dc));
        getDriver().manage().window().maximize();
        getDriver().get(getApplicationURL());
 
    }

    public WebDriver getDriver() {
        return driver.get();
    }


    @Override
    public void testFailure(Failure failure) {
        super.testFailure(failure);
        if (!failure.getDescription().isSuite()) {
        	attachFailedStep(getDriver());
        }
    }

    @Attachment( type = "image/png")
    private byte[] attachFailedStep(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }


    @After
    public void captureScreenshotIfFailed(Scenario scenario){

            if (scenario.isFailed()) {
            	scenario.embed(saveScreenshot(scenario.getName(), getDriver()), "image/png");
            }
        getDriver().quit();
    }


    @Attachment( value = "Screenshot of {0}",type = "image/png")
    public byte[] saveScreenshot(String name,WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }


}


