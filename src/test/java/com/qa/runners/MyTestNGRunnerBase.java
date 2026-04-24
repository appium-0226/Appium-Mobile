package com.qa.runners;

import com.qa.utils.DriverManager;
import com.qa.utils.GlobalParams;
import com.qa.utils.ServerManager;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.apache.logging.log4j.ThreadContext;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class MyTestNGRunnerBase {

    private static final ThreadLocal<TestNGCucumberRunner> testNGCucumberRunner = new ThreadLocal<>();

    public static TestNGCucumberRunner getRunner(){
        return testNGCucumberRunner.get();
    }

    public static void setRunner(TestNGCucumberRunner testNGCucumberRunner1){
        testNGCucumberRunner.set(testNGCucumberRunner1);
    }

    @Parameters({"platformName", "udid", "deviceName", "appiumPort", "systemPort",
            "chromeDriverPort", "wdaLocalPort", "webkitDebugProxyPort"})
    @BeforeClass(alwaysRun = true)
    public void setUpClass(String platformName, String udid, String deviceName, String appiumPort,
                           @Optional("") String systemPort,
                           @Optional("") String chromeDriverPort,
                           @Optional("") String wdaLocalPort,
                           @Optional("") String webkitDebugProxyPort) throws Exception {

        ThreadContext.put("ROUTINGKEY", platformName + "_" + deviceName);
        ThreadContext.put("RUN_TIMESTAMP", GlobalParams.getRunTimestamp());

        GlobalParams params = new GlobalParams();
        params.setPlatformName(platformName);
        params.setUdid(udid);
        params.setDeviceName(deviceName);
        params.setAppiumPort(appiumPort);

        switch (platformName){
            case "Android":
                params.setSystemPort(systemPort);
                params.setChromeDriverPort(chromeDriverPort);
                break;
            case "iOS":
                params.setWdaLocalPort(wdaLocalPort);
                params.setWebkitDebugProxyPort(webkitDebugProxyPort);
                break;
        }

        new ServerManager().startServer();
        new DriverManager().initializeDriver();

        setRunner(new TestNGCucumberRunner(this.getClass()));
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
    public void scenario(PickleWrapper pickle, FeatureWrapper cucumberFeature) {
        getRunner().runScenario(pickle.getPickle());
    }

    @DataProvider
    public Object[][] scenarios() {
        return getRunner().provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        DriverManager driverManager = new DriverManager();
        if (driverManager.getDriver() != null) {
            driverManager.getDriver().quit();
            driverManager.setDriver(null);
        }
        ServerManager serverManager = new ServerManager();
        if (serverManager.getServer() != null) {
            serverManager.getServer().stop();
        }
        getRunner().finish();

        // Copy reports to specific device run folder
        GlobalParams params = new GlobalParams();
        String platform = params.getPlatformName();
        String device = params.getDeviceName();

        File sourceHtml = new File("target" + File.separator + platform + File.separator + "report.html");
        File sourceJson = new File("target" + File.separator + platform + File.separator + "report.json");

        File destDir = new File("logs" + File.separator + GlobalParams.getRunTimestamp() + File.separator + platform + "_" + device + File.separator + "reports");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        try {
            if (sourceHtml.exists()) Files.copy(sourceHtml.toPath(), new File(destDir, "report.html").toPath(), StandardCopyOption.REPLACE_EXISTING);
            if (sourceJson.exists()) Files.copy(sourceJson.toPath(), new File(destDir, "report.json").toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
