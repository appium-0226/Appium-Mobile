package com.qa.utils;

import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.io.File;
import java.util.Properties;

public class CapabilitiesManager {

    public Object getCaps() throws Exception {
        GlobalParams params = new GlobalParams();
        Properties props = new PropertyManager().getProps();

        try {
            TestUtils.log().info("Loading capabilities...");
            switch (params.getPlatformName()) {

                case "Android":
                    String androidAppUrl = System.getProperty("user.dir")
                            + File.separator + "src"
                            + File.separator + "test"
                            + File.separator + "resources"
                            + File.separator + "apps"
                            + File.separator + props.getProperty("androidAppLocation");

                    TestUtils.log().info("Android App URL: {}", androidAppUrl);

                    return new UiAutomator2Options()
                            .setPlatformName(params.getPlatformName())
                            .setUdid(params.getUdid())
                            .setDeviceName(params.getDeviceName())
                            .setAutomationName(props.getProperty("androidAutomationName"))
                            .setAppPackage(props.getProperty("androidAppPackage"))
                            .setAppActivity(props.getProperty("androidAppActivity"))
                            .setSystemPort(Integer.parseInt(params.getSystemPort()))
                            .setChromedriverPort(Integer.parseInt(params.getChromeDriverPort()))
                            .setApp(androidAppUrl);

                case "iOS":
                    String iOSAppUrl = System.getProperty("user.dir")
                            + File.separator + "src"
                            + File.separator + "test"
                            + File.separator + "resources"
                            + File.separator + "apps"
                            + File.separator + props.getProperty("iOSAppLocation");

                    TestUtils.log().info("iOS App URL: {}", iOSAppUrl);

                    XCUITestOptions iosOptions = new XCUITestOptions()
                            .setPlatformName(params.getPlatformName())
                            .setUdid(params.getUdid())
                            .setDeviceName(params.getDeviceName())
                            .setAutomationName(props.getProperty("iOSAutomationName"))
                            .setBundleId(props.getProperty("iOSBundleId"))
                            .setWdaLocalPort(Integer.parseInt(params.getWdLocalPort()));

                    iosOptions.setCapability("webkitDebugProxyPort", Integer.parseInt(params.getWebkitDebugProxyPort()));
                    iosOptions.setApp(iOSAppUrl);

                    return iosOptions;

                default:
                    throw new Exception("Invalid platform name: " + params.getPlatformName());
            }

        } catch (Exception e) {
            TestUtils.log().fatal("Failed to create capabilities. ABORT!!! {}", e.toString());
            throw e;
        }
    }

}
