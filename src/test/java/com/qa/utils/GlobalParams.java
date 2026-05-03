package com.qa.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GlobalParams {

    // Global static run timestamp, generated ONCE per JVM execution (suite run)
    private static final String RUN_TIMESTAMP = "Run_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));

    private static ThreadLocal<String> platformName = new ThreadLocal<>();
    private static ThreadLocal<String> platformVersion = new ThreadLocal<>();
    private static ThreadLocal<String> udid = new ThreadLocal<>();
    private static ThreadLocal<String> deviceName = new ThreadLocal<>();
    private static ThreadLocal<String> appiumPort = new ThreadLocal<>();
    private static ThreadLocal<String> systemPort = new ThreadLocal<>();
    private static ThreadLocal<String> chromeDriverPort = new ThreadLocal<>();
    private static ThreadLocal<String> wdaLocalPort = new ThreadLocal<>();
    private static ThreadLocal<String> webkitDebugProxyPort = new ThreadLocal<>();

    public static String getRunTimestamp() {
        return RUN_TIMESTAMP;
    }

    public String getPlatformName() {
        return platformName.get();
    }

    public void setPlatformName(String platformName1) {
        platformName.set(platformName1);
    }

    public String getPlatformVersion() {
        return platformVersion.get();
    }

    public void setPlatformVersion(String platformVersion1) {
        platformVersion.set(platformVersion1);
    }

    public String getUdid() {
        return udid.get();
    }

    public void setUdid(String udid1) {
        udid.set(udid1);
    }

    public String getDeviceName() {
        return deviceName.get();
    }

    public void setDeviceName(String deviceName1) {
        deviceName.set(deviceName1);
    }

    public String getAppiumPort() {
        return appiumPort.get();
    }

    public void setAppiumPort(String appiumPort1) {
        appiumPort.set(appiumPort1);
    }

    public String getSystemPort() {
        return systemPort.get();
    }

    public void setSystemPort(String systemPort1) {
        systemPort.set(systemPort1);
    }

    public String getChromeDriverPort() {
        return chromeDriverPort.get();
    }

    public void setChromeDriverPort(String chromeDriverPort1) {
        chromeDriverPort.set(chromeDriverPort1);
    }

    public String getWdLocalPort() {
        return wdaLocalPort.get();
    }

    public void setWdaLocalPort(String wdaLocalPort1) {
        wdaLocalPort.set(wdaLocalPort1);
    }

    public String getWebkitDebugProxyPort() {
        return webkitDebugProxyPort.get();
    }

    public void setWebkitDebugProxyPort(String webkitDebugProxyPort1) {
        webkitDebugProxyPort.set(webkitDebugProxyPort1);
    }

    public void initializeGlobalParams() {
        GlobalParams params = new GlobalParams();
        params.setPlatformName(System.getProperty("platformName", "Android"));
        params.setPlatformVersion(System.getProperty("platformVersion", "15"));
        params.setUdid(System.getProperty("udid", "emulator-5554"));
        params.setDeviceName(System.getProperty("deviceName", "Pixel8Android15"));

        switch (params.getPlatformName()) {
            case "Android":
                params.setSystemPort(System.getProperty("systemPort", "10000"));
                params.setChromeDriverPort(System.getProperty("chromeDriverPort", "11000"));
                break;
            case "iOS":
                params.setWdaLocalPort(System.getProperty("wdLocalPort", "10001"));
                params.setWebkitDebugProxyPort(System.getProperty("webkitDebugProxyPort", "11001"));
                break;
            default:
                throw new IllegalStateException("Invalid platform name!");
        }

    }

}
