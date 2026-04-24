package com.qa.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.Scenario;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class VideoManager {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

    public void startRecording() {
        try {
            switch (new GlobalParams().getPlatformName()) {
                case "Android" -> ((AndroidDriver) new DriverManager().getDriver()).startRecordingScreen();
                case "iOS" -> ((IOSDriver) new DriverManager().getDriver()).startRecordingScreen();
            }
            TestUtils.log().info("Video recording started");
        } catch (Exception e) {
            TestUtils.log().error("Failed to start recording: {}", e.getMessage());
        }
    }

    public void stopRecording(Scenario scenario) {
        if (!scenario.isFailed()) {
            stopAndDiscard();
            return;
        }

        try {
            String base64Video = null;
            switch (new GlobalParams().getPlatformName()) {
                case "Android" -> base64Video = ((AndroidDriver) new DriverManager().getDriver()).stopRecordingScreen();
                case "iOS" -> base64Video = ((IOSDriver) new DriverManager().getDriver()).stopRecordingScreen();
            }

            if (base64Video != null) {
                saveVideo(base64Video, scenario.getName());
            }
        } catch (Exception e) {
            TestUtils.log().error("Failed to stop recording: {}", e.getMessage());
        }
    }

    private void stopAndDiscard() {
        try {
            switch (new GlobalParams().getPlatformName()) {
                case "Android" -> ((AndroidDriver) new DriverManager().getDriver()).stopRecordingScreen();
                case "iOS" -> ((IOSDriver) new DriverManager().getDriver()).stopRecordingScreen();
            }
            TestUtils.log().info("Scenario passed - video discarded");
        } catch (Exception e) {
            TestUtils.log().error("Failed to discard recording: {}", e.getMessage());
        }
    }

    private void saveVideo(String base64Video, String scenarioName) {
        try {
            GlobalParams params = new GlobalParams();
            String deviceFolder = params.getPlatformName() + "_" + params.getDeviceName();
            String timestamp = LocalDateTime.now().format(FORMATTER);
            String safeName = scenarioName.replaceAll("[^a-zA-Z0-9]", "_");

            File videoDir = new File("logs" + File.separator + GlobalParams.getRunTimestamp() + File.separator + deviceFolder + File.separator + "videos");
            if (!videoDir.exists()) {
                videoDir.mkdirs();
            }

            File videoFile = new File(videoDir, timestamp + "_" + safeName + ".mp4");
            Files.write(videoFile.toPath(), Base64.getDecoder().decode(base64Video));

            TestUtils.log().info("Video saved: {}", videoFile.getPath());
        } catch (Exception e) {
            TestUtils.log().error("Failed to save video: {}", e.getMessage());
        }
    }

}
