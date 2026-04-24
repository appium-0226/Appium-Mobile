package com.qa.utils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ServerManager {

    private static ThreadLocal<AppiumDriverLocalService> server = new ThreadLocal<>();

    public AppiumDriverLocalService getServer() {
        return server.get();
    }

    public void startServer() {
        TestUtils.log().info("starting appium server");

        AppiumDriverLocalService server = getAppiumService();
        server.start();

        if (!server.isRunning()) {
            TestUtils.log().info("Appium server not started. ABORT!!!");
            throw new AppiumServerHasNotBeenStartedLocallyException("Appium server not started. ABORT!!!");
        }

        server.clearOutPutStreams();
        this.server.set(server);

        TestUtils.log().info("Appium server started");
    }

    public AppiumDriverLocalService getAppiumService() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return windowsService();
        } else {
            return macService();
        }
    }

    private File getLogFile() {
        GlobalParams params = new GlobalParams();
        File logDir = new File("logs" + File.separator + GlobalParams.getRunTimestamp() + File.separator
                + params.getPlatformName() + "_" + params.getDeviceName());
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        return new File(logDir, "server.log");
    }

    private AppiumDriverLocalService windowsService() {
        GlobalParams params = new GlobalParams();
        int port = Integer.parseInt(params.getAppiumPort());
        return new AppiumServiceBuilder()
                .usingPort(port)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withLogFile(getLogFile())
                .build();
    }

    private AppiumDriverLocalService macService() {
        GlobalParams params = new GlobalParams();
        int port = Integer.parseInt(params.getAppiumPort());

        Map<String, String> environment = new HashMap<>();

        String currentPath = System.getenv("PATH");
        environment.put("PATH", currentPath + ":/usr/local/bin:/opt/homebrew/bin");
        
        if (System.getenv("ANDROID_HOME") != null) {
            environment.put("ANDROID_HOME", System.getenv("ANDROID_HOME"));
        }
        if (System.getenv("JAVA_HOME") != null) {
            environment.put("JAVA_HOME", System.getenv("JAVA_HOME"));
        }

        return new AppiumServiceBuilder()
                .withEnvironment(environment)
                .usingPort(port)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withLogFile(getLogFile())
                .build();
    }

}
