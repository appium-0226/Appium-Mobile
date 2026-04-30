package com.qa.stepdef;

import com.qa.utils.DriverManager;
import com.qa.utils.GlobalParams;
import com.qa.utils.ServerManager;
import com.qa.utils.VideoManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.ThreadContext;

public class Hooks {

    @Before
    public void initialize() throws Exception {
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
                scenario.attach(screenshot, "image/png", scenario.getName());
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
