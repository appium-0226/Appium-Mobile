package com.qa.stepdef;

import com.qa.utils.DriverManager;
import com.qa.utils.GlobalParams;
import com.qa.utils.ServerManager;
import com.qa.utils.VideoManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.ThreadContext;

import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;

public class Hooks {

    @Before
    public void initialize(Scenario scenario) throws Exception {
        GlobalParams params = new GlobalParams();
        Allure.parameter("Platform", params.getPlatformName());
        Allure.parameter("Device Name", params.getDeviceName());
        Allure.parameter("UDID", params.getUdid());

        Allure.label("host", params.getDeviceName());
        Allure.label("Device", params.getDeviceName());

        Allure.getLifecycle().updateTestCase(result -> {
            result.setName(result.getName() + " [" + params.getDeviceName() + "]");
            if (result.getHistoryId() != null) {
                result.setHistoryId(result.getHistoryId() + "_" + params.getDeviceName().replace(" ", "_"));
            }
        });
        
        com.qa.utils.DBManager.initialize();
        new VideoManager().startRecording();
    }

    @After
    public void quit(Scenario scenario) {
        new VideoManager().stopRecording(scenario);

        if (scenario.isFailed()) {
            try {
                byte[] screenshot = new DriverManager().getDriver()
                        .getScreenshotAs(org.openqa.selenium.OutputType.BYTES);
                Allure.addAttachment("Screenshot - " + scenario.getName(), "image/png", new ByteArrayInputStream(screenshot), "png");

            } catch (Exception e) {
                com.qa.utils.TestUtils.log().error("Gagal mengambil screenshot: " + e.getMessage());
            }
        }

        try {
            com.qa.utils.DBManager.close();
        } catch (Exception e) {
            com.qa.utils.TestUtils.log().error("Gagal menutup koneksi database: " + e.getMessage());
        }
    }

}
