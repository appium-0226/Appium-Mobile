package com.qa.runners;

import com.qa.utils.DriverManager;
import com.qa.utils.GlobalParams;
import com.qa.utils.ServerManager;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.apache.logging.log4j.ThreadContext;
import org.testng.ITestContext;
import org.testng.annotations.*;
import io.qameta.allure.testng.AllureTestNg;
import org.json.JSONObject;
import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Listeners({ AllureTestNg.class })
public class MyTestNGRunnerBase {

    private static final AtomicBoolean historyCopied = new AtomicBoolean(false);
    private static final AtomicInteger finishedThreads = new AtomicInteger(0);
    private static final AtomicInteger totalDevices = new AtomicInteger(0);

    private static final ThreadLocal<TestNGCucumberRunner> testNGCucumberRunner = new ThreadLocal<>();

    private static final ConcurrentHashMap<String, String> threadToDeviceMap = new ConcurrentHashMap<>();

    public static TestNGCucumberRunner getRunner() {
        return testNGCucumberRunner.get();
    }

    public static void setRunner(TestNGCucumberRunner testNGCucumberRunner1) {
        testNGCucumberRunner.set(testNGCucumberRunner1);
    }

    @Parameters({ "platformName", "platformVersion", "udid", "deviceName", "appiumPort", "systemPort",
            "chromeDriverPort", "wdaLocalPort", "webkitDebugProxyPort" })
    @BeforeClass(alwaysRun = true)
    public void setUpClass(ITestContext context, String platformName, String platformVersion, String udid,
            String deviceName,
            String appiumPort,
            @Optional("Android") String systemPort,
            @Optional("Android") String chromeDriverPort,
            @Optional("iOS") String wdaLocalPort,
            @Optional("iOS") String webkitDebugProxyPort) throws Exception {

        if (totalDevices.get() == 0) {
            int count = context.getSuite().getXmlSuite().getTests().size();
            totalDevices.set(count);
            System.out.println("Total devices detected from XML: " + count);
        }

        ThreadContext.put("ROUTINGKEY", platformName + "_" + deviceName);
        ThreadContext.put("RUN_TIMESTAMP", GlobalParams.getRunTimestamp());

        GlobalParams params = new GlobalParams();
        params.setPlatformName(platformName);
        params.setPlatformVersion(platformVersion);
        params.setUdid(udid);
        params.setDeviceName(deviceName);
        params.setAppiumPort(appiumPort);

        switch (platformName) {
            case "Android":
                params.setSystemPort(systemPort);
                params.setChromeDriverPort(chromeDriverPort);
                break;
            case "iOS":
                params.setWdaLocalPort(wdaLocalPort);
                params.setWebkitDebugProxyPort(webkitDebugProxyPort);
                break;
        }

        if (historyCopied.compareAndSet(false, true)) {
            copyHistoryToResults();
        }

        new ServerManager().startServer();
        new DriverManager().initializeDriver();

        setRunner(new TestNGCucumberRunner(this.getClass()));

        threadToDeviceMap.put(Thread.currentThread().getName(), deviceName);

        setupAllureEnvironment(platformName, platformVersion, deviceName);
    }

    private void setupAllureEnvironment(String platform, String version, String device) {
        File allureResultsDir = new File("target/allure-results");
        if (!allureResultsDir.exists())
            allureResultsDir.mkdirs();

        File envFile = new File(allureResultsDir, "environment.properties");
        try (java.io.FileOutputStream fos = new java.io.FileOutputStream(envFile, true)) {
            String entry = String.format("%s_%s=%s (v%s)\n", platform, device.replace(" ", "_"), device, version);
            fos.write(entry.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        GlobalParams params = new GlobalParams();
        String device = params.getDeviceName();

        patchAllureResults(device);

        int finished = finishedThreads.incrementAndGet();
        int total = totalDevices.get();
        System.out.println("Device " + device + " finished. (" + finished + "/" + total + ")");

        if (finished >= total) {
            System.out.println("All devices finished. Archiving results and generating unified report...");
            archiveAllureResults();
            generateAllureReport();
        }
    }

    private void copyHistoryToResults() {
        File historySource = new File("allure-report/history");
        File historyDest = new File("target/allure-results/history");
        if (!historySource.exists()) return;
        if (!historyDest.exists()) historyDest.mkdirs();
        try {
            File[] files = historySource.listFiles();
            if (files == null) return;
            for (File file : files) {
                Files.copy(file.toPath(),
                        new File(historyDest, file.getName()).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateAllureReport() {
        try {
            String allureBin = ".allure/allure-2.30.0/bin/allure";
            ProcessBuilder pb = new ProcessBuilder(
                    allureBin, "generate", "target/allure-results",
                    "--clean", "-o", "allure-report");
            pb.inheritIO();
            pb.start().waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void archiveAllureResults() {
        File sourceDir = new File("target/allure-results");
        File destDir = new File("logs" + File.separator + GlobalParams.getRunTimestamp()
                + File.separator + "allure-results");

        if (!sourceDir.exists())
            return;
        if (!destDir.exists())
            destDir.mkdirs();

        try {
            File[] files = sourceDir.listFiles();
            if (files == null)
                return;
            for (File file : files) {
                Files.copy(file.toPath(),
                        new File(destDir, file.getName()).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void patchAllureResults(String device) {
        File allureResultsDir = new File("target/allure-results");
        if (!allureResultsDir.exists())
            return;

        String currentThread = threadToDeviceMap.entrySet().stream()
                .filter(e -> e.getValue().equals(device))
                .map(java.util.Map.Entry::getKey)
                .findFirst().orElse(null);

        String deviceTag = device.replace(" ", "_");
        File[] resultFiles = allureResultsDir.listFiles(
                (dir, name) -> name.endsWith("-result.json"));
        if (resultFiles == null)
            return;

        for (File resultFile : resultFiles) {
            try {
                String content = new String(Files.readAllBytes(resultFile.toPath()), StandardCharsets.UTF_8);
                JSONObject json = new JSONObject(content);

                String currentName = json.optString("name", "");
                if (currentName.contains("[" + device + "]")) {
                    continue;
                }

                if (currentThread != null) {
                    JSONArray threadCheckLabels = json.optJSONArray("labels");
                    boolean belongsToThisDevice = false;
                    if (threadCheckLabels != null) {
                        for (int i = 0; i < threadCheckLabels.length(); i++) {
                            JSONObject label = threadCheckLabels.getJSONObject(i);
                            if ("thread".equals(label.optString("name")) &&
                                    label.optString("value", "").contains(currentThread)) {
                                belongsToThisDevice = true;
                                break;
                            }
                        }
                    }
                    if (!belongsToThisDevice)
                        continue;
                }

                json.put("name", currentName + " [" + device + "]");

                String historyId = json.optString("historyId", "");
                if (!historyId.isEmpty()) {
                    json.put("historyId", historyId + "_" + deviceTag);
                }
                String fullName = json.optString("fullName", "");
                if (!fullName.isEmpty()) {
                    json.put("fullName", fullName + "_" + deviceTag);
                }

                JSONArray labels = json.optJSONArray("labels");
                if (labels != null) {
                    boolean hasParentSuite = false;
                    for (int i = 0; i < labels.length(); i++) {
                        JSONObject label = labels.getJSONObject(i);
                        String labelName = label.optString("name");
                        if ("host".equals(labelName)) {
                            label.put("value", device);
                        }
                        if ("parentSuite".equals(labelName)) {
                            label.put("value", device);
                            hasParentSuite = true;
                        }
                    }

                    if (!hasParentSuite) {
                        JSONObject parentSuiteLabel = new JSONObject();
                        parentSuiteLabel.put("name", "parentSuite");
                        parentSuiteLabel.put("value", device);
                        labels.put(parentSuiteLabel);
                    }

                    JSONObject deviceLabel = new JSONObject();
                    deviceLabel.put("name", "Device");
                    deviceLabel.put("value", device);
                    labels.put(deviceLabel);
                    json.put("labels", labels);
                }

                Files.write(resultFile.toPath(), json.toString().getBytes(StandardCharsets.UTF_8));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
