package com.qa.utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.net.URL;
public class DriverManager {

    private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    public AppiumDriver getDriver() {
        return driver.get();
    }

    public void setDriver(AppiumDriver driver2) {
        driver.set(driver2);
    }

    public void initializeDriver() throws Exception {
        GlobalParams params = new GlobalParams();
        Object capabilities = new CapabilitiesManager().getCaps();
        AppiumDriver newDriver = null;

        URL serverUrl = new ServerManager().getServer().getUrl();

        switch (params.getPlatformName()) {
            case "Android":
                newDriver = new AndroidDriver(serverUrl, (UiAutomator2Options) capabilities);
                break;
            case "iOS":
                newDriver = new IOSDriver(serverUrl, (XCUITestOptions) capabilities);
                break;
            default:
                throw new Exception("Invalid platform: " + params.getPlatformName());
        }

        setDriver(newDriver);
    }
}
