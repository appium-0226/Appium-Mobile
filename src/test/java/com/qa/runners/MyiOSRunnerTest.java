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

@CucumberOptions(plugin = {
                "pretty",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
                }
        , features = {"src/test/resources"}
        , glue = {"com.qa.stepdef"}
        , dryRun = false
        , monochrome = true
        , tags = "@test"
)
public class MyiOSRunnerTest extends MyTestNGRunnerBase {

}
