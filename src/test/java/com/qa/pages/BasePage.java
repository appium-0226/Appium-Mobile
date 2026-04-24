package com.qa.pages;

import com.qa.utils.DriverManager;
import com.qa.utils.GlobalParams;
import com.qa.utils.PropertyManager;
import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Properties;
import java.util.HashMap;
import java.util.List;

public class BasePage {

    private AppiumDriver driver;

    public BasePage() {
        this.driver = new DriverManager().getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(this.driver), this);
    }

    public void waitForVisibility(WebElement e) {
        new WebDriverWait(driver, Duration.ofSeconds(TestUtils.WAIT))
                .until(ExpectedConditions.visibilityOf(e));
    }

    public void waitForVisibility(By e) {
        new WebDriverWait(driver, Duration.ofSeconds(TestUtils.WAIT))
                .until(ExpectedConditions.visibilityOfElementLocated(e));
    }

    public void clear(WebElement e) {
        waitForVisibility(e);
        e.clear();
    }

    public void click(WebElement e) {
        waitForVisibility(e);
        e.click();
    }

    public void click(WebElement e, String msg) {
        waitForVisibility(e);
        TestUtils.log().info(msg);
        e.click();
    }

    public void click(By e, String msg) {
        waitForVisibility(e);
        TestUtils.log().info(msg);
        driver.findElement(e).click();
    }

    public void sendKeys(WebElement e, String txt) {
        waitForVisibility(e);
        e.sendKeys(txt);
    }

    public void sendKeys(WebElement e, String txt, String msg) {
        waitForVisibility(e);
        TestUtils.log().info(msg);
        e.sendKeys(txt);
    }

    public String getAttribute(WebElement e, String attribute) {
        waitForVisibility(e);
        return e.getAttribute(attribute);
    }

    public String getAttribute(By e, String attribute) {
        waitForVisibility(e);
        return driver.findElement(e).getAttribute(attribute);
    }

    public String getText(WebElement e, String msg) {
        String txt = switch (new GlobalParams().getPlatformName()) {
            case "Android" -> getAttribute(e, "text");
            case "iOS" -> getAttribute(e, "label");
            default -> throw new IllegalStateException("Unexpected platform: " + new GlobalParams().getPlatformName());
        };
        TestUtils.log().info("{}: {}", msg, txt);
        return txt;
    }

    public void closeApp() throws Exception {
        Properties props = new PropertyManager().getProps();
        switch (new GlobalParams().getPlatformName()) {
            case "Android" -> {
                HashMap<String, String> args = new HashMap<>();
                args.put("appId", props.getProperty("androidAppPackage"));
                driver.executeScript("mobile: terminateApp", args);
            }
            case "iOS" -> {
                HashMap<String, String> args = new HashMap<>();
                args.put("bundleId", props.getProperty("iOSBundleId"));
                driver.executeScript("mobile: terminateApp", args);
            }
            default -> throw new IllegalStateException("Unexpected platform: " + new GlobalParams().getPlatformName());
        }
    }

    public void launchApp() throws Exception {
        Properties props = new PropertyManager().getProps();
        switch (new GlobalParams().getPlatformName()) {
            case "Android" -> {
                HashMap<String, String> args = new HashMap<>();
                args.put("appId", props.getProperty("androidAppPackage"));
                driver.executeScript("mobile: activateApp", args);
            }
            case "iOS" -> {
                HashMap<String, String> args = new HashMap<>();
                args.put("bundleId", props.getProperty("iOSBundleId"));
                driver.executeScript("mobile: activateApp", args);
            }
            default -> throw new IllegalStateException("Unexpected platform: " + new GlobalParams().getPlatformName());
        }
    }

    public WebElement andScrollToElementUsingUiScrollable(String childLocAttr, String childLocValue) {
        return driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
                        + "new UiSelector()." + childLocAttr + "(\"" + childLocValue + "\"));"));
    }

    public WebElement iOSScrollToElementUsingMobileScroll(WebElement e) {
        HashMap<String, String> scrollObject = new HashMap<>();
        scrollObject.put("element", ((RemoteWebElement) e).getId());
        scrollObject.put("toVisible", "true");
        driver.executeScript("mobile:scroll", scrollObject);
        return e;
    }

    public By iOSScrollToElementUsingMobileScrollParent(WebElement parentE, String predicateString) {
        HashMap<String, String> scrollObject = new HashMap<>();
        scrollObject.put("element", ((RemoteWebElement) parentE).getId());
        scrollObject.put("predicateString", predicateString);
        driver.executeScript("mobile:scroll", scrollObject);
        return AppiumBy.iOSNsPredicateString(predicateString);
    }

    public WebElement scrollToElement(WebElement element, String direction) {
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.width * 0.5);
        int startY = direction.equals("up") ? (int) (size.height * 0.6) : (int) (size.height * 0.4);
        int endY = direction.equals("up") ? (int) (size.height * 0.4) : (int) (size.height * 0.6);

        for (int i = 0; i < 3; i++) {
            if (find(element, 1)) return element;
            swipe(startX, startY, startX, endY, 1000);
        }
        throw new RuntimeException("Element not found after scrolling");
    }

    public WebElement scrollToElement(By element, String direction) {
        Dimension size = driver.manage().window().getSize();
        int startX = (int) (size.width * 0.5);
        int startY = direction.equals("up") ? (int) (size.height * 0.6) : (int) (size.height * 0.4);
        int endY = direction.equals("up") ? (int) (size.height * 0.4) : (int) (size.height * 0.6);

        for (int i = 0; i < 3; i++) {
            if (find(element, 1)) return driver.findElement(element);
            swipe(startX, startY, startX, endY, 1000);
        }
        throw new RuntimeException("Element not found after scrolling");
    }

    public boolean find(WebElement element, int timeout) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean find(By element, int timeout) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(timeout))
                    .until(ExpectedConditions.visibilityOfElementLocated(element));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void swipe(int startX, int startY, int endX, int endY, int duration) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1)
                .addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY))
                .addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()))
                .addAction(finger.createPointerMove(Duration.ofMillis(duration), PointerInput.Origin.viewport(), endX, endY))
                .addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(List.of(swipe));
    }

}
